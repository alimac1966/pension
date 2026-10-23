// Groups that are not spending (moved money / money in)
const EXCLUDE = ['Income', 'Transfers'];
// Row order (anything else is appended, largest first)
const ORDER = ['Repayments','Bills and utilities','Transport','Shopping','Entertainment',
               'Gifts and giving','Business','Household','Health'];
const MORTGAGE_CATEGORY = 'Mortgages';
const MONTHS = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];

const $ = id => document.getElementById(id);
$('btn').onclick = () => $('file').click();
$('file').onchange = e => {
  const f = e.target.files[0];
  if (!f) return;
  const r = new FileReader();
  r.onload = () => { try { build(parseCSV(r.result)); $('msg').textContent = f.name; }
                     catch (err) { $('msg').textContent = 'Error: ' + err.message; } };
  r.readAsText(f);
};

function parseCSV(text) {
  const rows = []; let row = [], cur = '', q = false;
  for (let i = 0; i < text.length; i++) {
    const c = text[i];
    if (q) {
      if (c === '"' && text[i+1] === '"') { cur += '"'; i++; }
      else if (c === '"') q = false;
      else cur += c;
    } else if (c === '"') q = true;
    else if (c === ',') { row.push(cur); cur = ''; }
    else if (c === '\n' || c === '\r') {
      if (c === '\r' && text[i+1] === '\n') i++;
      row.push(cur); cur = '';
      if (row.length > 1 || row[0] !== '') rows.push(row);
      row = [];
    } else cur += c;
  }
  if (cur !== '' || row.length) { row.push(cur); rows.push(row); }
  const head = rows.shift().map(h => h.trim().toUpperCase());
  return rows.map(r => Object.fromEntries(head.map((h, i) => [h, r[i]])));
}

function sum(o) { return Object.values(o).reduce((a, b) => a + b, 0); }
const esc = t => String(t).replace(/[&<>"]/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;'}[c]));

function build(data) {
  const cats = {}, mort = {}, monthSet = new Set();   // cats[group][category][month]
  for (const r of data) {
    const g = r['CATEGORY GROUP'];
    if (!g || EXCLUDE.includes(g)) continue;
    const amt = parseFloat(r['AMOUNT']);
    if (isNaN(amt) || !r['DATE']) continue;
    const c = r['CATEGORY'] || '(none)';
    const m = r['DATE'].slice(0, 7);               // YYYY-MM
    monthSet.add(m);
    const spend = -amt;                            // spending positive, refunds negative
    const cg = ((cats[g] ??= {})[c] ??= {});
    cg[m] = (cg[m] || 0) + spend;
    if (c === MORTGAGE_CATEGORY) mort[m] = (mort[m] || 0) + spend;
  }
  const months = [...monthSet].sort();
  if (!months.length) throw new Error('No spending rows found');

  // Monthly totals per group (sum of its categories)
  const grp = {};
  for (const g in cats) {
    grp[g] = {};
    for (const c in cats[g]) for (const m in cats[g][c]) grp[g][m] = (grp[g][m] || 0) + cats[g][c][m];
  }

  const groups = Object.keys(grp).sort((a, b) => {
    const ia = ORDER.indexOf(a), ib = ORDER.indexOf(b);
    if (ia >= 0 && ib >= 0) return ia - ib;
    if (ia >= 0) return -1;
    if (ib >= 0) return 1;
    return sum(grp[b]) - sum(grp[a]);
  });

  const label = m => MONTHS[+m.slice(5) - 1] + '-' + m.slice(2, 4);
  const fmt = v => {
    const r = Math.round(v);                       // whole pounds, avoids -0
    const s = '£' + Math.abs(r).toLocaleString('en-GB') + '.00';
    return r < 0 ? '-' + s : s;
  };
  const cell = v => `<td class="${Math.round(v) < 0 ? 'neg' : ''}">${fmt(v)}</td>`;
  const n = months.length;

  let h = '<table><thead><tr><th></th>' + months.map(m => `<th>${label(m)}</th>`).join('') +
          '<th>Total</th><th>Month Ave</th></tr></thead><tbody>';

  const nums = vals => {
    const t = vals.reduce((a, b) => a + b, 0);
    return vals.map(cell).join('') + cell(t) + cell(t / n);
  };

  groups.forEach((g, gi) => {
    h += `<tr class="group" data-g="${gi}" tabindex="0" aria-expanded="false">` +
         `<td><span class="caret">▸</span>${esc(g)}</td>${nums(months.map(m => grp[g][m] || 0))}</tr>`;
    Object.keys(cats[g]).sort((a, b) => sum(cats[g][b]) - sum(cats[g][a])).forEach(c => {
      h += `<tr class="cat" data-p="${gi}" hidden><td>${esc(c)}</td>${nums(months.map(m => cats[g][c][m] || 0))}</tr>`;
    });
  });

  const totals = months.map(m => groups.reduce((a, g) => a + (grp[g][m] || 0), 0));
  h += `<tr class="total"><td>Total</td>${nums(totals)}</tr>`;
  h += `<tr class="total2"><td>Without Mortgage</td>${nums(totals.map((t, i) => t - (mort[months[i]] || 0)))}</tr>`;
  h += '</tbody></table>';
  $('out').innerHTML = h;
  $('toggleAll').hidden = false;
  $('toggleAll').textContent = 'Expand all';
}

function setOpen(row, open) {
  row.setAttribute('aria-expanded', open);
  row.classList.toggle('open', open);
  $('out').querySelectorAll(`tr.cat[data-p="${row.dataset.g}"]`).forEach(r => r.hidden = !open);
}

$('out').addEventListener('click', e => {
  const row = e.target.closest('tr.group');
  if (row) setOpen(row, row.getAttribute('aria-expanded') !== 'true');
});
$('out').addEventListener('keydown', e => {
  const row = e.target.closest('tr.group');
  if (row && (e.key === 'Enter' || e.key === ' ')) { e.preventDefault(); row.click(); }
});

$('toggleAll').onclick = () => {
  const open = $('toggleAll').textContent === 'Expand all';
  $('out').querySelectorAll('tr.group').forEach(r => setOpen(r, open));
  $('toggleAll').textContent = open ? 'Collapse all' : 'Expand all';
};
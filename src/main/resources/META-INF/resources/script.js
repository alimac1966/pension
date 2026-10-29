  // ---- 1. Views: each one returns the HTML for a pane ----
  // Add your own here, then add a button with a matching data-view.
  const views = {
    users: () => `
      <h2>Users</h2>
      <table>
        <tr><th>Name</th><th>Role</th><th>Status</th></tr>
        <tr><td>Ada</td><td>Admin</td><td><span class="badge ok">Active</span></td></tr>
        <tr><td>Grace</td><td>Editor</td><td><span class="badge warn">Pending</span></td></tr>
        <tr><td>Linus</td><td>Viewer</td><td><span class="badge bad">Suspended</span></td></tr>
      </table>`,
    notes: () => `
      <h2 class="section-title">Pension Parameters</h2>
        <div class="input-grid">
            <div class="field">
                <label>Pension Balance</label>
                <input id="pensionBalance" type="number" >
            </div>
            <div class="field">
                <label>Growth Rate</label>
                <input id="growthRate" type="number" >
            </div>
            <div class="field">
                <label>Inflation</label>
                <input id="inflation" type="number" >
            </div>
            <div class="field">
                <label>Mortgage Balance</label>
                <input id="mortgageBalance" type="number" >
            </div>
            <div class="field">
                <label>Annual Spending</label>
                <input id="spending" type="number" >
            </div>
            <div class="field">
                <label>Savings</label>
                <input id="savings" type="number" >
            </div>
            <div class="field">
                <label>Tax Allowance</label>
                <input id="taxAllowance" type="number" >
            </div>
            <div class="field">
                <label>Stat Pension</label>
                <input id="statePension" type="number" >
            </div>

        </div>
        <button type="button" class="run-action" id="run-projection">Pension Projection</button>
        <button type="button" class="run-action" id="run-stressTest">Stress Test</button>

      `,
    notesLower: () => `
    <section class="results-wrapper">
        <h2>Results</h2>
        <div class="table-container">
            <table class="results-table">
                <thead>
                <tr>
                    <th>Age</th>
                    <th>Tax-Free</th>
                    <th>Taxable</th>
                    <th>Spending</th>
                    <th>Income (TF)</th>
                    <th>Income (Tax)</th>
                    <th>State Pension</th>
                    <th>Other</th>
                </tr>
                </thead>
                <tbody id="resultsTable"></tbody>
            </table>
        </div>
    </section>
    `,
    log: () => {
      const now = new Date().toLocaleTimeString();
      return `
      <h2>Log</h2>
      <pre><span class="t">${now}</span>  <span class="lvl-info">INFO</span>  Loaded log view
<span class="t">${now}</span>  <span class="lvl-warn">WARN</span>  Nothing else to report</pre>`;
    }
  };


  
  
  // ---- 2. The two controls for a pane: show() and clear() ----
  const panes = {
    upper: document.getElementById("upper"),
    lower: document.getElementById("lower")
  };
  const mainEl = document.querySelector("main");

  // Hide panes without a selected view, and highlight the active view buttons.
  function updateLayout() {
    const upperVisible = Boolean(panes.upper.dataset.view);
    const lowerVisible = Boolean(panes.lower.dataset.view);
    mainEl.classList.toggle("no-upper", !upperVisible && lowerVisible);
    mainEl.classList.toggle("no-lower", upperVisible && !lowerVisible);
    mainEl.classList.toggle("no-panels", !upperVisible && !lowerVisible);
    document.querySelectorAll("button[data-pane]").forEach(btn => {
      const { pane, view } = btn.dataset;
      btn.classList.toggle("active", view !== "" && panes[pane].dataset.view === view);
    });
  }

  function show(pane, view) {
    panes[pane].innerHTML = views[view]();
    panes[pane].dataset.view = view;
    updateLayout();
  }

  function clear(pane) {
    panes[pane].innerHTML = "";
    delete panes[pane].dataset.view;
    updateLayout();
  }

  const longevityDefaults = {
    pensionBalance: 466208,
    growthRate: 0.06,
    inflation: 0.04,
    mortgageBalance: 59514.31,
    spending: 30000,
    savings: 15000,
    taxAllowance: 12570,
    statePension: 12570
  };

  function getValueOrDefault(id) {
    const input = document.getElementById(id);
    const value = input.value.trim();
    return value === "" ? longevityDefaults[id] : Number(value);
  }

  function renderResults(rows) {
    const table = document.getElementById("resultsTable");
    table.replaceChildren();

    rows.forEach(row => {
      const tr = document.createElement("tr");
      const values = [
        row.myAge,
        Number(row.taxFreePension).toFixed(2),
        Number(row.taxablePension).toFixed(2),
        Number(row.spending).toFixed(2),
        Number(row.incomeTFPension).toFixed(2),
        Number(row.incomeTaxPension).toFixed(2),
        Number(row.incomeStatePension).toFixed(2),
        Number(row.incomeOther).toFixed(2)
      ];

      values.forEach(value => {
        const td = document.createElement("td");
        td.textContent = value;
        tr.appendChild(td);
      });

      table.appendChild(tr);
    });
  }

  async function runProjection() {
    show("lower", lowerViewMap[panes.upper.dataset.view]);
    const resultsTable = document.getElementById("resultsTable");
    const loadingRow = resultsTable.insertRow();
    const loadingCell = loadingRow.insertCell();
    loadingCell.colSpan = 8;
    loadingCell.textContent = "Calculating...";

    const request = {
        pensionBalance: getValueOrDefault("pensionBalance"),
        growthRate: getValueOrDefault("growthRate"),
        inflation: getValueOrDefault("inflation"),
        mortgageBalance: getValueOrDefault("mortgageBalance"),
        spending: getValueOrDefault("spending"),
        savings: getValueOrDefault("savings"),
        taxAllowance: getValueOrDefault("taxAllowance"),
        statePension: getValueOrDefault("statePension")
    };

    try {
        const response = await fetch("/projection", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(request)
        });

        if (!response.ok) {
            throw new Error(`Projection request failed (${response.status})`);
        }

        const rows = await response.json();
        if (!Array.isArray(rows)) {
            throw new Error("Projection response was not a list of results");
        }

        renderResults(rows);
    } catch (err) {
        console.error("Projection request failed:", err);
        resultsTable.replaceChildren();
        const errorRow = resultsTable.insertRow();
        const errorCell = errorRow.insertCell();
        errorCell.colSpan = 8;
        errorCell.textContent = "Unable to load results. Check the server and try again.";
    }
  };

  const lowerViewMap = {
    notes: "notesLower"
  };

  // ---- 3. Wire up the header buttons ----
  document.querySelectorAll("button[data-pane]").forEach(btn => {
    btn.addEventListener("click", () => {
      const { pane, view } = btn.dataset;

      if (!view) {
        clear(pane);
        return;
      }

      if (pane === "upper") {
        clear("lower");
      }

      show(pane, view);
    });
  });

  document.getElementById("clear-all").addEventListener("click", () => {
    clear("upper");
    clear("lower");
  });

  panes.upper.addEventListener("click", event => {
    if (event.target instanceof Element && event.target.closest("#run-projection")) {
      runProjection();
    }
  });

  // ---- 4. Draggable divider (mouse, touch and keyboard) ----
  const divider = document.getElementById("divider");
  const upperPanel = document.getElementById("upper-panel");

  function setSplit(px) {
    const max = mainEl.clientHeight - 14 - 90;    // keep room for the lower panel
    const clamped = Math.max(90, Math.min(px, max));
    document.documentElement.style.setProperty("--split", clamped + "px");
  }

  divider.addEventListener("pointerdown", e => {
    divider.setPointerCapture(e.pointerId);
    const onMove = ev => setSplit(ev.clientY - mainEl.getBoundingClientRect().top);
    const onUp = () => {
      divider.removeEventListener("pointermove", onMove);
      divider.removeEventListener("pointerup", onUp);
    };
    divider.addEventListener("pointermove", onMove);
    divider.addEventListener("pointerup", onUp);
  });

  divider.addEventListener("keydown", e => {
    const current = upperPanel.getBoundingClientRect().height + 16;
    if (e.key === "ArrowUp")   { setSplit(current - 30); e.preventDefault(); }
    if (e.key === "ArrowDown") { setSplit(current + 30); e.preventDefault(); }
  });

  // Both panes start hidden until a view is selected.
  updateLayout();

const DEFAULTS = {
    pensionBalance: 500000,
    growthRate: 0.06,
    inflation: 0.04,
    mortgageBalance: 50000,
    spending: 30000,
    savings: 15000,
    taxAllowance: 12570,
    statePension: 12570
};

function getValueOrDefault(id) {
    const el = document.getElementById(id);
    const raw = el.value.trim();

    if (raw === "") {
        return DEFAULTS[id];
    }

    return parseFloat(raw);
}

async function runStressTest() {

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
        const response = await fetch("/stress-test/detailed", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(request)
        });

        if (!response.ok) {
            alert("Error running stress test");
            return;
        }

        const rows = await response.json();

        renderResults(rows);

    } catch (err) {
        console.error("Fetch error:", err);
        alert("Failed to contact server");
    }
}

function renderResults(rows) {
    const table = document.getElementById("resultsTable");
    table.innerHTML = "";

    rows.forEach(row => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${row.myAge}</td>
            <td>${row.taxFreePension.toFixed(2)}</td>
            <td>${row.taxablePension.toFixed(2)}</td>
            <td>${row.spending.toFixed(2)}</td>
            <td>${row.incomeTFPension.toFixed(2)}</td>
            <td>${row.incomeTaxPension.toFixed(2)}</td>
            <td>${row.incomeStatePension.toFixed(2)}</td>
            <td>${row.incomeOther.toFixed(2)}</td>
        `;

        table.appendChild(tr);
    });
}






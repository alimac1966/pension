async function runStressTest() {

    const request = {
        pensionBalance: parseFloat(document.getElementById("pensionBalance").value),
        growthRate: parseFloat(document.getElementById("growthRate").value),
        inflation: parseFloat(document.getElementById("inflation").value),
        mortgageBalance: parseFloat(document.getElementById("mortgageBalance").value),
        spending: parseFloat(document.getElementById("spending").value),
        savings: parseFloat(document.getElementById("savings").value),
        taxAllowance: parseFloat(document.getElementById("taxAllowance").value),
        statePension: parseFloat(document.getElementById("statePension").value)
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





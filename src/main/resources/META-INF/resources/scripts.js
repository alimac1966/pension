async function runProjection() {
    clearResults();

    const body = {
        pensionBalance: parseFloat(document.getElementById("pensionBalance").value),
        growthRate: parseFloat(document.getElementById("growthRate").value),
        mortgageBalance: parseFloat(document.getElementById("mortgageBalance").value),
        mortgageInterestRate: parseFloat(document.getElementById("mortgageInterestRate").value),
        mortgageMonthlyPayment: parseFloat(document.getElementById("mortgageMonthlyPayment").value),
        monthlyDrawdown: parseFloat(document.getElementById("monthlyDrawdown").value)
    };

    const response = await fetch("/pension/projection", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(body)
    });

    const result = await response.json();

    document.getElementById("projectionResult").innerHTML =
        `<h3>Projection Result</h3>
         <p>Months Until Depletion: <strong>${result.monthsUntilDepletion}</strong></p>
         <p>Years Until Depletion: <strong>${result.yearsUntilDepletion}</strong></p>`;
}

async function runStressTest() {
    clearResults();

    const body = {
        pensionBalance: parseFloat(document.getElementById("pensionBalance").value),
        defaultGrowthRate: parseFloat(document.getElementById("growthRate").value),
        mortgageBalance: parseFloat(document.getElementById("mortgageBalance").value),
        defaultInterestRate: parseFloat(document.getElementById("mortgageInterestRate").value),
        mortgageMonthlyPayment: parseFloat(document.getElementById("mortgageMonthlyPayment").value),
        monthlyDrawdown: parseFloat(document.getElementById("monthlyDrawdown").value)
    };

    const response = await fetch("/pension/stress-test", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(body)
    });

    const result = await response.json();

    document.getElementById("stressTestResult").innerHTML =
        `<h3>Stress Test Result</h3>
         <p>Start Year: <strong>${result.startYear}</strong></p>
         <p>Months Until Depletion: <strong>${result.monthsUntilDepletion}</strong></p>
         <p>Years Until Depletion: <strong>${result.yearsUntilDepletion}</strong></p>`;
}

async function runStressTestAllYears() {
    clearResults();

    const body = {
        pensionBalance: parseFloat(document.getElementById("pensionBalance").value),
        defaultGrowthRate: parseFloat(document.getElementById("growthRate").value),
        mortgageBalance: parseFloat(document.getElementById("mortgageBalance").value),
        defaultInterestRate: parseFloat(document.getElementById("mortgageInterestRate").value),
        mortgageMonthlyPayment: parseFloat(document.getElementById("mortgageMonthlyPayment").value),
        monthlyDrawdown: parseFloat(document.getElementById("monthlyDrawdown").value)
    };

    const response = await fetch("/pension/stress-test/all", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(body)
    });

    const results = await response.json();

    const tableBody = document.querySelector("#stressTestTable tbody");
    tableBody.innerHTML = "";

    results.forEach(row => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${row.startYear}</td>
            <td>${row.monthsUntilDepletion}</td>
            <td>${row.yearsUntilDepletion}</td>
        `;

        tableBody.appendChild(tr);
    });
}

async function runDetailedStressTest() {
    clearResults();

    const body = {
        pensionBalance: parseFloat(document.getElementById("pensionBalance").value),
        defaultGrowthRate: parseFloat(document.getElementById("growthRate").value),
        mortgageBalance: parseFloat(document.getElementById("mortgageBalance").value),
        defaultInterestRate: parseFloat(document.getElementById("mortgageInterestRate").value),
        mortgageMonthlyPayment: parseFloat(document.getElementById("mortgageMonthlyPayment").value),
        monthlyDrawdown: parseFloat(document.getElementById("monthlyDrawdown").value)
    };

    const response = await fetch("/pension/stress-test/detailed", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(body)
    });

    const results = await response.json();

    const tableBody = document.querySelector("#stressTestTable tbody");
    tableBody.innerHTML = "";

    results.forEach(row => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${row.startYear}</td>
            <td>${row.monthsUntilDepletion}</td>
            <td>${row.yearsUntilDepletion}</td>
        `;

        tableBody.appendChild(tr);
    });
}

function clearResults() {
    document.getElementById("projectionResult").innerHTML = "";
    document.getElementById("stressTestResult").innerHTML = "";

    const tableBody = document.querySelector("#stressTestTable tbody");
    if (tableBody) {
        tableBody.innerHTML = "";
    }
}

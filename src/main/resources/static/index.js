const API_BASE_URL = "http://localhost:8080"; // 로컬 환경에서 명확하게 설정

async function createECard() {
    const message = document.getElementById("message").value;

    if (!message) {
        alert("Please enter a message!");
        return;
    }

    const response = await fetch(`${API_BASE_URL}/ecard`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ message: message })
    });

    const data = await response.json();

    if (response.ok) {
        document.getElementById("ecardLink").innerHTML =
            `<a href="${data.shareableLink}" target="_blank">${data.shareableLink}</a>`;
    } else {
        document.getElementById("ecardLink").innerText = "Failed to create e-card.";
    }
}
 async function createECard() {
        const message = document.getElementById("message").value;

        if (!message) {
            alert("Please enter a message!");
            return;
        }

        const response = await fetch("http://localhost:8080/ecard", {
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
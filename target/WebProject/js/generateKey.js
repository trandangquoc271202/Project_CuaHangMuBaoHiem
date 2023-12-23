document.getElementById("genKey").addEventListener("click", function () {
    fetch("/Project_CuaHangMuBaoHiem_war/doGenerateKey")
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}, Text: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            const publicKey = document.getElementById("public_key");
            const privateKey = document.getElementById("private_key");

            if ((!publicKey.value && !privateKey.value) || (publicKey.value && privateKey.value)) {
                publicKey.value = data.publicK;
                privateKey.value = data.privateK;
            } else if (!publicKey.value) {
                publicKey.value = data.publicK;
            } else if (!privateKey.value) {
                privateKey.value = data.privateK;
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error: " + error.message);
        });
});
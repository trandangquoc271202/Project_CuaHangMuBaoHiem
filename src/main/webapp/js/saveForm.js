function saveForm() {
    var publicKey = document.getElementById("public_key").value;
    document.getElementById("publicKeyInput").value = publicKey;
    document.getElementById("myForm").submit();
}
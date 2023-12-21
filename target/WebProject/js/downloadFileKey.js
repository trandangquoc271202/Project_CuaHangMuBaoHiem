document.getElementById("downloadKey").addEventListener("click", function () {
    var publicKey = document.getElementById("public_key").value.replace(/\s/g, "");
    var privateKey = document.getElementById("private_key").value.replace(/\s/g, "");

    if (publicKey == "" || privateKey == "") {
        alert("Thông tin về khóa chưa đầy đủ, tải file thất bại!");
        return; // Kết thúc hàm nếu thông tin khóa không đầy đủ
    }

    var fileContent = "";
    fileContent += "*** HUONG DAN SU DUNG KHOA CONG KHAI VA KHOA RIENG TU ***\n\n\n\n\n";
    fileContent += "Khoa cong khai giong nhu chiec chia khoa ma ban co the mo mot canh cua cho moi nguoi vao, ai cung co the biet chia khoa do nhu the nao, ra sao.\n";
    fileContent += "No duoc su dung de ma hoa thong diep cua ban, giu cho no an toan khi di chuyen qua mang. Khi ban muon nhan thong diep, nguoi khac se su dung khoa cong khai cua ban de ma hoa no.\n\n";
    fileContent += "Khoa rieng tu giong nhu mot chiec chia khoa ma chi co minh ban giu no.\n"
    fileContent += "No duoc su dung de giai ma thong diep da duoc ma hoa bang khoa cong khai. No la cong cu de thuc hien cac giao dich, xac thuc danh tinh va chu ky so dien tu. Moi nguoi chi co mot khoa rieng tu duy nhat va bao ve no la rat quan trong.\n\n\n\n"
    fileContent += "Day la KHOA CONG KHAI cua ban:\n";
    fileContent += publicKey + "\n\n";
    fileContent += "Day la KHOA RIENG TU cu ban:\n";
    fileContent += privateKey + "\n\n\n\n\n";
    fileContent += "*** Vui long KHONG CHIA SE KHOA RIENG TU cho bat ky ai ***\n"
    fileContent += "Cam on quy khach, Helmet Shop!"
    // fileContent += "Public Key: " + publicKey + "\nPrivate Key: " + privateKey;
    var blob = new Blob([fileContent], {type: "text/plain"});

    var downloadLink = document.createElement("a");
    downloadLink.href = URL.createObjectURL(blob);
    downloadLink.download = "Helmet-key.txt";
    document.body.appendChild(downloadLink);

    downloadLink.click();

    document.body.removeChild(downloadLink);
});

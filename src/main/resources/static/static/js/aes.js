var KEY = "qwertyuiop123456";

function encrypt_str(str) {
    var key = CryptoJS.enc.Utf8.parse(KEY); // 秘钥
    var encrypted = CryptoJS.AES.encrypt(str, key, {
        // iv: iv,
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7,
    });
    return encrypted.toString();
};

function decrypt_str(str) {
    var key = CryptoJS.enc.Utf8.parse(KEY); // 秘钥
    var decrypted = CryptoJS.AES.decrypt(str, key, {
        // iv: iv,
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7,
    });
    return decrypted.toString(CryptoJS.enc.Utf8);
};
export function aesEncode(str, aesKey) {
    if (typeof aesKey !== "string")
        aesKey = aesKey.toString()
    // aesKey不足16位的补足
    if (aesKey.length !== 16) {
        let flag = aesKey.length
        while (flag !== 16) {
            flag < 16 ? aesKey = aesKey + '6' : aesKey = aesKey.slice(0, aesKey.length - 1)
            flag = aesKey.length
        }
    }
    // var key = CryptoJS.enc.Utf8.parse(aesKey);
    // 默认的 KEY 与 iv
    const srcs = CryptoJS.enc.Utf8.parse(str)
    const KEY = CryptoJS.enc.Utf8.parse(aesKey) // ''中与后台一样  密码
    const IV = CryptoJS.enc.Utf8.parse(aesKey) // ''中与后台一样
    var encryptedData = CryptoJS.AES.encrypt(srcs, KEY, {
        iv:IV,
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return encryptedData.toString();
}
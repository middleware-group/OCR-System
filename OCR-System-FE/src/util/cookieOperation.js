
export default {addCookie, getCookie, delCookie};

//添加cookie
function addCookie(objName, objValue, objHours) {
    var str = objName + "=" + decodeURI(objValue);
    if (objHours > 0) { //为 0 时不设定过期时间，浏览器关闭时 cookie 自动消失
        var date = new Date();
        var ms = objHours * 3600 * 1000;
        date.setTime(date.getTime() + ms);
        str += "; expires=" + date.toUTCString();
    }
    document.cookie = str;
}

// 获取指定名称的cookie的值
function getCookie(key) {
    var arrStr = document.cookie.split("; ");
    for (var i = 0; i < arrStr.length; i++) {
        var temp = arrStr[i].split("=");
        if (temp[0] == key)
            return decodeURI(temp[1]);
    }
    return "";
}

// 为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
function delCookie(key) {
    this.addCookie(key, "", 0);
}
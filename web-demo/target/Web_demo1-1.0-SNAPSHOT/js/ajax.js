var axios = {};

axios.get = function (url, params, callback){
    var ajax;
    if (window.XMLHttpRequest){
        ajax = new XMLHttpRequest();
    }else{
        ajax = new ActiveXObject("Microsoft.XMLHTTP");
    }

    ajax.onreadystatechange = function (){
        console.info(ajax.readyState, ajax.status);
        if (ajax.readyState == 4 && ajax.status == 200){
            var res = JSON.parse(ajax.responseText);
            callback(res);
        }
    }

    if (params) {
        if (url.includes("?") == false) {
            url += "?";
        }
        for (let name in params.params) {
            url += "&" + name + "=" + params.params[name];
        }
    }
    console.info(url);

    ajax.open("get",url);
    ajax.send();

}
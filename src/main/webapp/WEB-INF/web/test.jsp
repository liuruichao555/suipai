<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <script>
        var eventSource = new EventSource("/sse");
        eventSource.onmessage = function(event) {
            console.log(event.data);
        };
    </script>

    <%--<input type="file" id="myFile" multiple />
    <input type="button" value="submit" onclick="upload()" />
    <script>
        function upload() {
            var xmlhttp;
            if (window.XMLHttpRequest) {
                xmlhttp = new XMLHttpRequest();
            } else {
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            var myFile = document.getElementById('myFile');
            var formData = new FormData();
            for (var i = 0; i < myFile.files.length; i++) {
                formData.append('file', myFile.files[i]);
            }
            //formData.append('file', myFile.files[0]);
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    var result = xmlhttp.responseText;
                    console.log('result: ' + result);
                }
            };
            xmlhttp.open('POST', '/upload', true);
            xmlhttp.send(formData);
        }
    </script>--%>
</body>
</html>

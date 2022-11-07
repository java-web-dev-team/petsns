let finalPath;

function profileUploadBtn(){
    let formData = new FormData();
    let inputFile = $("input[type='file']");
    let files = inputFile[0].files;

    formData.append("profileImg", files[0]);
    const token = $("meta[name='_csrf']").attr("content");      // html 에 저장된 meta 값 불러오기 , 내용에 content 저장
    const header = $("meta[name='_csrf_header']").attr("content");
    console.log("files = ->" + files);
    console.log("inputFile -> " + inputFile);
    console.log("profileUploadBtn ------> ");

    $.ajax({
        url:"/memberImg/upload",
        processData: false,
        contentType: false,
        data: formData,
        type: 'POST',
        async: false,
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function(path){
            console.log("result = -> " + path);
            finalPath = path;
            console.log("finalPath =-> " + finalPath);
            $("#profileImg").attr('th:value', path);
        },
        error: function(jqxHR, textStatus){
            console.log("textStatus = " + textStatus);
        }
    });
};


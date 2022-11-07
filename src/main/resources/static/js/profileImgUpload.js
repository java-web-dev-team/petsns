function profileUploadBtn(){
    let inputFile = $("input[type='file']");
    const token = $("meta[name='_csrf']").attr("content");      // html 에 저장된 meta 값 불러오기 , 내용에 content 저장
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url:"/memberImg/upload",
        processData: false,
        contentType: false,
        data: {file : inputFile},
        type: 'POST',
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token);
        },

        success: function(result){
            $("#ImgURL").attr("profileImg", result);
        },
        error: function(jqxHR, textStatus, errorThrown){
            console.log(textStatus);
        }
    });
};


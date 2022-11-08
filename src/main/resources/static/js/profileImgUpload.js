$(document).ready(function (e) {
    let memberDtoResult;
    let fileCheck = true;
    let str = "";
    const token = $("meta[name='_csrf']").attr("content");      // html 에 저장된 meta 값 불러오기 , 내용에 content 저장
    const header = $("meta[name='_csrf_header']").attr("content");

    let regex = new RegExp("(.*?)\.(exe|sh|zip|alz|tiff)$");
    let maxSize = 10485760;     // 10MB

    function checkExtension(fileName, fileSize) {
        if (fileSize >= maxSize) {
            alert("파일 사이즈 초과")
            return false;
        }

        if (regex.test(fileName)) {
            alert("해당 종류의 파일은 업로드 할 수 없습니다.");
            return false;
        }
        return true;
    }

    $(".profileUploadBtn").on("change", function () {

        let fileName = $(this).val().split("\\").pop();
        const changeImgFile = document.getElementById("changeImgFile");
        $(this).siblings(".custom-file-label1").addClass("selected").html(fileName);


        let formData = new FormData();
        let inputFile = $("input[type='file']");
        let files = inputFile[0].files;
        let appended = false;
        if (!checkExtension(fileName[0].name, files[0].size)) {
        } else {
            appended = true;
            formData.append("profileImg", files[0]);
        }

        if (!appended) {
            return;
        }

        console.log("files = ->" + files);
        console.log("inputFile -> " + inputFile);
        console.log("profileUploadBtn ------> ");
        console.log("fileName =-> " + fileName)

        $.ajax({
            url: "/memberImg/upload",
            processData: false,
            contentType: false,
            data: formData,
            type: 'POST',
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },

            success: function (result) {
                console.log("result = -> " + result);
                    memberDtoResult = result;
                changeImgFile.disabled = false;
            },
            error: function (jqxHR, textStatus) {
                console.log("textStatus = " + textStatus);
            }
        });
    })

    $(".primary-btn").on("click", function (e) {
        alert("회원가입이 완료되었습니다.");
        e.preventDefault();
        if(document.getElementById("profileImg") == null)  {
            str = "<input type='hidden' name='profileImg' value='profile_normal.png'>";
        }  else{
            str = "<input type='hidden' name='profileImg' value='" + memberDtoResult.uuid + "_" + memberDtoResult.imgName + "'>";

        }
        console.log("primary-btn str -> " + str)

        $(".str-box").html(str);
        $("form1").submit();
    });

    $(".img-primary-btn").on("click", function (e) {
        alert("사진이 변경되었습니다.");
        e.preventDefault();

        str = "<input type='hidden' name='profileImg' value='" + memberDtoResult.uuid + "_" + memberDtoResult.imgName + "'>";
        console.log("primary-btn str -> " + str)

        $(".str-box1").html(str);
        $("#form2").submit();
    });

});

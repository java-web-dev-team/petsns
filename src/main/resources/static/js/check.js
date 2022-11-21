
// 아이디 중복확인
function idCheck(){
    const nickname = $("#nickname").val();
    const token = $("meta[name='_csrf']").attr("content");      // html 에 저장된 meta 값 불러오기 , 내용에 content 저장
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type:"POST",
        data:{nickname : nickname},
        url: "/idCheck",
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(count){
            if(count > 0){
                $("#msgId").html("중복된 닉네임 입니다.");
                $("#msgId").css('color', 'red');
                document.getElementById("login-button2").disabled = true;
            } else if(count == 0){
                $("#msgId").html("사용 가능한 닉네임 입니다.");
                $("#msgId").css('color', 'green');
            }

        }
    })
}

function memberCheck(){
    var memberName = $("#memberName").val();
    if (memberName == null || memberName == " " || memberName == "") {
        return false;
    }
    const token = $("meta[name='_csrf']").attr("content");      // html 에 저장된 meta 값 불러오기 , 내용에 content 저장
    const header = $("meta[name='_csrf_header']").attr("content");


    $.ajax({
        type: "POST",
        data: {memberName: memberName},
        url: "/searchMember",
        dataType: 'json',
        beforeSend(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(data){
            var str = "";
            for (let i = 0; i<data.length; i++){
                str += "<div style='border: 1px solid black;' href='/member/profile/"+ data[i].email +"'>";
                str += "<img src='/img/profile-picture/" + data[i].profileImg + "' alt=' ' style='width: 25%; height: 30%; vertical-align: bottom;'>"
                str += "<a style='height: 15px; width: 15px;' aria-valuetext='" + data[i].username + "'></a>";
                str += "</div>";
            }
            $(".smallBox").html(str);
        }
    })
}


// 이메일 중복확인
function emailCheck(){
    const email = $("#email").val();
    const certBtn = document.getElementById("certificationBtn");
    const token = $("meta[name='_csrf']").attr("content");      // html 에 저장된 meta 값 불러오기 , 내용에 content 저장
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type:"POST",
        data:{email : email},
        url: "/emailCheck",
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(count){
            if(count > 0){
                $("#msg").html("중복된 이메일 입니다.");
                $("#msg").css('color', 'red');
            } else if (count == 0){
                $("#msg").html("사용 가능한 이메일 입니다.");
                $("#msg").css('color', 'green');
                certBtn.disabled = false;
            } else{
                $("#msg").html("이메일 형식에 맞춰 다시 입력해주세요. ex)aaa@aaa.com");
                $("#msg").css('color', 'red');
            }

        }
    })
}

// 비밀번호 체크
function pwdCheck(){
    const password = $("#pwd").val();
    const token = $("meta[name='_csrf']").attr("content");      // html 에 저장된 meta 값 불러오기 , 내용에 content 저장
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: "/pwdCheck",
        data: {password, password},
        type: "post",
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            if(result == 1){
                $("#pwdMsg").html("현재 비밀번호와 같습니다.");
                $("#pwdMsg").css("color", "green");
            } else{
                $("#pwdMsg").html("현재 비밀번호와 다릅니다.");
                $("#pwdMsg").css("color", "red");
            }
        }

    })
}

function check_pw(){
    if (document.getElementById("password").value > 0) {
        document.getElementById("password").style.color = 'green';
    }
}

function check_pw_re(){

    if (document.getElementById("password").value == document.getElementById("passwordCheck").value) {
        document.getElementById("passwordCheck").style.color = 'green';
        $("#CheckPwd").html("비밀번호가 일치합니다.");
        $("#CheckPwd").css("color", "green");
    } else{
        document.getElementById("passwordCheck").style.color = 'red';
        $("#CheckPwd").html("비밀번호가 일치하지 않습니다.");
        $("#CheckPwd").css("color", "red");
    }
}

let check_mail_certification;

function mail_post_btn() {
        const token = $("meta[name='_csrf']").attr("content");      // html 에 저장된 meta 값 불러오기 , 내용에 content 저장
        const header = $("meta[name='_csrf_header']").attr("content");
        const email = $("#email").val();
        console.log("mail_check_btn() ----- > " + email);
        $.ajax({
            url: "/email/Certification",         // GET 방식이라 뒤에 url 붙이기 가능.
            type: "POST",
            data: {email: email},
            beforeSend(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                console.log("data ==-> " + data);
                $("#emailMsg").html("인증번호가 전송되었습니다.");
                $("#emailMsg").css("color", "green");
                check_mail_certification = data;
                console.log("check_mail_certification ->>" + check_mail_certification);
            }
        })
}

function mail_certification_check(){
    const check_mail = $("#check_mail").val();
    const target = document.getElementById("login-button2");
    console.log("check_mail ->" + check_mail)
    if(check_mail == check_mail_certification && (document.getElementById("password").value == document.getElementById("passwordCheck").value)){
            target.disabled = false;
    } else{
        target.disabled = true;
    }

}


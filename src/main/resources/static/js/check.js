
// 아이디 중복확인
function idCheck(){
    const nickname = $("#nickname").val();
    const token = $("meta[name='_csrf']").attr("content");      // html 에 저장된 meta 값 불러오기 , 내용에 content 저장
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type:"POST",
        data:{nickname : nickname},
        url: "/idCheck",
        async:false,
        beforeSend : function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(count){
            if(count > 0){
                $("#msgId").html("중복된 닉네임 입니다.");
                $("#msgId").css('color', 'red');
            } else if(count == 0){
                $("#msgId").html("사용 가능한 닉네임 입니다.");
                $("#msgId").css('color', 'green');
            }

        }
    })
}


// 이메일 중복확인
function emailCheck(){
    const email = $("#email").val();
    const token = $("meta[name='_csrf']").attr("content");      // html 에 저장된 meta 값 불러오기 , 내용에 content 저장
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type:"POST",
        data:{email : email},
        url: "/emailCheck",
        async:false,
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
            } else{
                $("#msg").html("이메일 형식에 맞춰 다시 입력해주세요. ex)aaa@aaa.com");
                $("#msg").css('color', 'red');
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
    } else{
        document.getElementById("passwordCheck").style.color = 'red';
    }
}
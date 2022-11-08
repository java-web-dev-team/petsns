package javawebdev.petsns.member.dto;

import lombok.Data;

@Data

public class MemberDto {

    private String uuid;
    private String imgName;

    public MemberDto(){
    }

    public MemberDto(String uuid, String imgName){
        this.uuid = uuid;
        this.imgName = imgName;
    }
}

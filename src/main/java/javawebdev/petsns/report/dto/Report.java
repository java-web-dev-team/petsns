package javawebdev.petsns.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class Report {

    private Integer id;
    @Setter private String reporter;
    @Setter private String reported;
    @Setter private String content;
    @Setter private LocalDateTime registeredAt;
    @Setter private boolean check;

    public Report(String reporter, String reported, String content) {
        this.reporter = reporter;
        this.reported = reported;
        this.content = content;
        this.registeredAt = LocalDateTime.now();
        this.check = false;
    }
}

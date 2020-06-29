package ru.job4j.pingera.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.pingera.models.Task;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {

    private String name1;
    private String sellist1;
    private Integer cnt;
    private Integer packetsize;
    private Integer ttl;
    private Integer timeout;
    private String text2;
    private String date1;
    private Integer text3;
    private String sellist2;
    private String sellist3;
    private String sellist4;
    private String text4;
    private Integer total;

    public Task convertToTask(UserDto udto) {
        Task result = new Task();

        result.setUser(udto.convert());
        result.setName1(this.getName1());
        result.setSellist1(this.getSellist1());
        result.setCnt(this.getCnt());
        result.setPacketsize(this.getPacketsize());
        result.setTtl(this.getTtl());
        result.setTimeout(this.getTimeout());
        result.setText2(this.getText2());
        result.setDate1(Timestamp.valueOf(this.getDate1().replace("T", " ") + ":00"));
        result.setText3(this.getText3());
        result.setSellist2(this.getSellist2());
        result.setSellist3(this.getSellist3());
        result.setSellist4(this.getSellist4());
        result.setText4(this.getText4());
        result.setTotal(this.getTotal());

        return result;
    }

}

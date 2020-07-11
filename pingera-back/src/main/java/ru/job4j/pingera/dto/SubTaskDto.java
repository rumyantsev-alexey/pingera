package ru.job4j.pingera.dto;

import lombok.Getter;
import lombok.Setter;
import ru.job4j.pingera.models.SubTask;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

@Setter
@Getter
public class SubTaskDto {

        private Timestamp date1;

        private String result;

        private boolean Successfully;

        public SubTaskDto convert(SubTask st) {
            SubTaskDto result = new SubTaskDto();
            result.setDate1(st.getDate1());
            result.setSuccessfully(st.isSuccessfully());
            result.setResult(new String(st.getResult(), StandardCharsets.UTF_8));
            return result;
        }

}

package vn.edu.hcmuaf.fit.model;

import java.time.LocalDateTime;

public class FormData {
    private String id_kh;
    private LocalDateTime date;
    private String cf_code;

    public FormData(String id_kh, LocalDateTime date, String cf_code) {
        this.id_kh = id_kh;
        this.date = date;
        this.cf_code = cf_code;
    }

    public String getId_kh() {
        return id_kh;
    }

    public void setId_kh(String id_kh) {
        this.id_kh = id_kh;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCf_code() {
        return cf_code;
    }

    public void setCf_code(String cf_code) {
        this.cf_code = cf_code;
    }
}

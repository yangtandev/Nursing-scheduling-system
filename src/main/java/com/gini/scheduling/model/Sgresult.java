package com.gini.scheduling.model;

import com.gini.scheduling.dao.SgresultRepository;
import com.gini.scheduling.dao.SgruserRepository;
import com.gini.scheduling.utils.UUIDGenerator;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.gini.scheduling.constraint.SchedulingDifficultyComparator;
import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

// 人員排班結果表
@Entity
@PlanningEntity(difficultyComparatorClass = SchedulingDifficultyComparator.class)
@IdClass(SgresultId.class)
public class Sgresult {
    @PlanningId
    @Id
    @Column(columnDefinition = "CHAR(22) NOT NULL WITH DEFAULT")
    private String schuuid = UUIDGenerator.generateUUID22();
    
    // 排班日期 yyyy/MM/dd
    @Column(columnDefinition = "DATE NOT NULL WITH DEFAULT '0001-01-01'")
    private LocalDate schdate;

    // 排班日期所在週
    @Column(columnDefinition = "SMALLINT NOT NULL WITH DEFAULT")
    private int schweek;
    
    // 班別編號 55, D6, A0, A8, OFF, 公休
    @Column(columnDefinition = "CHAR(006) NOT NULL WITH DEFAULT")
    private String clsno;

    // 使用者卡號
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumns(value = {
        @JoinColumn(name = "uno", referencedColumnName = "uno", columnDefinition = "CHAR(005) NOT NULL WITH DEFAULT"),
        @JoinColumn(name = "clsnohid", referencedColumnName = "hid", columnDefinition = "CHAR(003) NOT NULL CHECK (CLSNOHID NOT IN ('   '))")
    }, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Sgruser sgruser;

    // 工作時數 0 or 8
    @Column(columnDefinition = "SMALLINT NOT NULL WITH DEFAULT 8")
    private int clspr;

    // 加班順序 0 (無加班) or 1~24
    @Column(columnDefinition = "SMALLINT NOT NULL WITH DEFAULT 0")
    private int overtime;

    // 醫院代碼
    @Id
    @Column(columnDefinition = "CHAR(003) NOT NULL CHECK (HID NOT IN ('   '))")
    private String hid = "2A0";

    public Sgresult() {
    }

    public Sgresult(
        Sgruser sgruser,
        LocalDate schdate,
        int schweek,
        String clsno
    ) {
        this.sgruser = sgruser;
        this.schdate = schdate;
        this.schweek = schweek;
        this.clsno = clsno;
    }


    // ************************************************************************
    // Getters and setters
    // ************************************************************************
    public String getSchuuid() {
        return this.schuuid.trim();
    }

    public String getUno() {
        return sgruser.getUno();
    }

    public String getUteam() {
        return sgruser.getUteam();
    }

    public LocalDate getSchdate() {
        return schdate;
    }

    public void setSchdate(LocalDate schdate) {
        this.schdate = schdate;
    }

    public int getSchweek() {
        return schweek;
    }

    public void setSchweek(int schweek) {
        this.schweek = schweek;
    }


    public String getClsno() {
        return clsno.trim();
    }

    public void setClsno(String clsno) {
        this.clsno = clsno.trim();
    }

    @PlanningVariable(valueRangeProviderRefs = "sgruserRange")
    public Sgruser getSgruser() {
        return sgruser;
    }

    public void setSgruser(Sgruser sgruser) {
        this.sgruser = sgruser;
    }

    public int getClspr() {
        return clspr;
    }

    public void setClspr(int clspr) {
        this.clspr = clspr;
    }

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public Boolean isWeekend() {
        DayOfWeek day = DayOfWeek.of(this.schdate.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }

    public Boolean isShiftWork() {
        return  (this.getClsno().equals("55") && this.isWeekend()) || this.getClsno().equals("D6") || this.getClsno().equals("A0") ;
    }

    public Boolean isUnAvailable() {
        return this.getClsno().equals("公休");
    }
}

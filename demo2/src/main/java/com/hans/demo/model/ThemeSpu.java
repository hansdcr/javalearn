package com.hans.demo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "theme_spu", schema = "missyou", catalog = "")
public class ThemeSpu {
    private int id;
    private int themeId;
    private int spuId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "theme_id")
    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    @Basic
    @Column(name = "spu_id")
    public int getSpuId() {
        return spuId;
    }

    public void setSpuId(int spuId) {
        this.spuId = spuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThemeSpu themeSpu = (ThemeSpu) o;
        return id == themeSpu.id &&
                themeId == themeSpu.themeId &&
                spuId == themeSpu.spuId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, themeId, spuId);
    }
}

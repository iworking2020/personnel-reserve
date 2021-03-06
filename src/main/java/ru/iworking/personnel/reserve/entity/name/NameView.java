package ru.iworking.personnel.reserve.entity.name;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.iworking.personnel.reserve.interfaces.name.INameView;
import ru.iworking.personnel.reserve.utils.LocaleUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Embeddable
public class NameView implements INameView, Serializable {

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name="name_view")
    @MapKeyColumn(name="locale")
    protected Map<Locale, String> namesView;

    @JsonIgnore
    @Override
    public String getName(Locale locale) {
        return namesView.get(locale);
    }

    @Override
    public Map<Locale, String> getNames() {
        return namesView;
    }
    public void setNames(Map<Locale, String> namesView) {
        this.namesView = namesView;
    }

    @JsonIgnore
    @Override
    public String getName() {
        return namesView.get(LocaleUtil.getInstance().getDefault());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NameView nameView = (NameView) o;
        return Objects.equals(namesView, nameView.namesView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namesView);
    }

    @Override
    public String toString() {
        return "NameView{" +
                "namesView=" + namesView +
                '}';
    }
}

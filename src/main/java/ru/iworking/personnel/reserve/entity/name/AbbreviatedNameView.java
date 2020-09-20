package ru.iworking.personnel.reserve.entity.name;

import ru.iworking.personnel.reserve.interfaces.name.IAbbreviatedNameView;
import ru.iworking.personnel.reserve.utils.LocaleUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Embeddable
public class AbbreviatedNameView implements IAbbreviatedNameView, Serializable {

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name="abbreviated_name_view")
    @MapKeyColumn(name="locale")
    protected Map<Locale, String> abbreviatedNamesView;

    @Override
    public String getName(Locale locale) {
        return abbreviatedNamesView.get(locale);
    }

    @Override
    public Map<Locale, String> getNames() {
        return abbreviatedNamesView;
    }
    public void setNames(Map<Locale, String> namesView) {
        this.abbreviatedNamesView = namesView;
    }

    @Override
    public String getName() {
        return getName(LocaleUtil.getInstance().getDefault());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbbreviatedNameView nameView = (AbbreviatedNameView) o;
        return Objects.equals(abbreviatedNamesView, nameView.abbreviatedNamesView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abbreviatedNamesView);
    }

    @Override
    public String toString() {
        return "NameView{" +
                "abbreviatedNamesView=" + abbreviatedNamesView +
                '}';
    }

}

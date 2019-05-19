package de.hfu.pms.utils;

import de.hfu.pms.shared.dto.UniversityDTO;
import de.hfu.pms.shared.enums.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * This utility class provides helper methods to make use of the WrappedEntity class that is used for
 * data representation in the graphical user interface.
 */
public class RepresentationWrapper {

    /**
     * Used to get the actual string representation that are shown in the gui.
     */
    private static ResourceBundle bundle = GuiLoader.getResourceBundle();

    /**
     * @return a collection with all family status values and their string representation.
     */
    public static Collection<WrappedEntity<FamilyStatus>> getWrappedFamilyStatus() {
        Collection<WrappedEntity<FamilyStatus>> collection = new ArrayList<>(FamilyStatus.values().length);
        collection.add(new WrappedEntity<>(bundle.getString("enum.familyStatus.single"), FamilyStatus.Single));
        collection.add(new WrappedEntity<>(bundle.getString("enum.familyStatus.married"), FamilyStatus.Married));
        collection.add(new WrappedEntity<>(bundle.getString("enum.familyStatus.no_info"), FamilyStatus.NoInformation));
        return collection;
    }

    public static Collection<WrappedEntity<Salutation>> getWrappedSalutations() {
        Collection<WrappedEntity<Salutation>> collection = new ArrayList<>(Salutation.values().length);
        collection.add(new WrappedEntity<>(bundle.getString("enum.salutation.mr"), Salutation.Mr));
        collection.add(new WrappedEntity<>(bundle.getString("enum.salutation.miss"), Salutation.Miss));
        collection.add(new WrappedEntity<>(bundle.getString("enum.salutation.none"), Salutation.None));
        return collection;
    }

    public static Collection<WrappedEntity<Gender>> getWrappedGenders() {
        Collection<WrappedEntity<Gender>> collection = new ArrayList<>(Gender.values().length);
        collection.add(new WrappedEntity<>(bundle.getString("enum.gender.male"), Gender.Male));
        collection.add(new WrappedEntity<>(bundle.getString("enum.gender.female"), Gender.Female));
        collection.add(new WrappedEntity<>(bundle.getString("enum.gender.miscellaneous"), Gender.Miscellaneous));
        collection.add(new WrappedEntity<>(bundle.getString("enum.gender.none"), Gender.No_Information));
        return collection;
    }

    public static Collection<WrappedEntity<Graduation>> getWrappedGraduations() {
        Collection<WrappedEntity<Graduation>> collection = new ArrayList<>();
        collection.add(new WrappedEntity<>(bundle.getString("enum.graduation.master_of_science"), Graduation.Master_of_Science));
        collection.add(new WrappedEntity<>(bundle.getString("enum.graduation.master_of_arts"), Graduation.Master_of_Arts));
        return collection;
    }


    public static Collection<WrappedEntity<DoctoralGraduation>> getWrappedDoctoralGraduations() {
        Collection<WrappedEntity<DoctoralGraduation>> collection = new ArrayList<>();
        collection.add(new WrappedEntity<>(bundle.getString("enum.doctoral_graduation.dr_ing"), DoctoralGraduation.Dr_Ing));
        collection.add(new WrappedEntity<>(bundle.getString("enum.doctoral_graduation.dr_phil"), DoctoralGraduation.Dr_Phil));
        collection.add(new WrappedEntity<>(bundle.getString("enum.doctoral_graduation.dr_rer_nat"), DoctoralGraduation.Dr_rer_nat));
        collection.add(new WrappedEntity<>(bundle.getString("enum.doctoral_graduation.dr_sc_nat"), DoctoralGraduation.Dr_sc_nat));
        collection.add(new WrappedEntity<>(bundle.getString("enum.doctoral_graduation.dr_rer_soc"), DoctoralGraduation.Dr_rer_soc));
        collection.add(new WrappedEntity<>(bundle.getString("enum.doctoral_graduation.dr_rer_tech"), DoctoralGraduation.Dr_rer_tech));
        collection.add(new WrappedEntity<>(bundle.getString("enum.doctoral_graduation.dr_sc_techn"), DoctoralGraduation.Dr_sc_techn));
        collection.add(new WrappedEntity<>(bundle.getString("enum.doctoral_graduation.ph_d"), DoctoralGraduation.Ph_D));
        return collection;
    }

    public static Collection<WrappedEntity<FacultyHFU>> getWrappedHFUFaculties() {
        Collection<WrappedEntity<FacultyHFU>> collection = new ArrayList<>();
        collection.add(new WrappedEntity<>(bundle.getString("enum.hfu_faculty.informatik"), FacultyHFU.Informatik));
        collection.add(new WrappedEntity<>(bundle.getString("enum.hfu_faculty.wirtschaftsinformatik"), FacultyHFU.Wirtschaftsinformatik));
        collection.add(new WrappedEntity<>(bundle.getString("enum.hfu_faculty.digitale_medien"), FacultyHFU.Digitale_Medien));
        collection.add(new WrappedEntity<>(bundle.getString("enum.hfu_faculty.industrial_technologies"), FacultyHFU.Industrial_Technologies));
        collection.add(new WrappedEntity<>(bundle.getString("enum.hfu_faculty.medical_and_life_sciences"), FacultyHFU.Medical_and_Life_Sciences));
        collection.add(new WrappedEntity<>(bundle.getString("enum.hfu_faculty.wirtschaft"), FacultyHFU.Wirtschaft));
        collection.add(new WrappedEntity<>(bundle.getString("enum.hfu_faculty.wirtschaftsingenieurwesen"), FacultyHFU.Wirtschaftsingenieurwesen));
        collection.add(new WrappedEntity<>(bundle.getString("enum.hfu_faculty.gesundheit_sicherheit_gesellschaft"), FacultyHFU.Gesundheit_Sicherheit_Gesellschaft));
        collection.add(new WrappedEntity<>(bundle.getString("enum.hfu_faculty.mechanical_and_medical_engineering"), FacultyHFU.Mechanical_and_Medical_Engineering));
        return collection;
    }

    public static Collection<WrappedEntity<Rating>> getWrappedRatings() {
        Collection<WrappedEntity<Rating>> collection = new ArrayList<>();
        collection.add(new WrappedEntity<>(bundle.getString("enum.rating.magna_cum_laude"), Rating.Magna_cum_laude));
        collection.add(new WrappedEntity<>(bundle.getString("enum.rating.cum_laude"), Rating.Cum_laude));
        collection.add(new WrappedEntity<>(bundle.getString("enum.rating.bene"), Rating.Bene));
        collection.add(new WrappedEntity<>(bundle.getString("enum.rating.rite"), Rating.Rite));
        collection.add(new WrappedEntity<>(bundle.getString("enum.rating.failed"), Rating.Failed));
        return collection;
    }

    public static Collection<WrappedEntity<UniversityDTO>> getWrappedUniversities(Collection<UniversityDTO> universities) {
        Collection<WrappedEntity<UniversityDTO>> collection = new ArrayList<>();
        for (UniversityDTO uni : universities) {
            collection.add(getWrappedUniversity(uni));
        }
        return collection;
    }

    public static WrappedEntity<UniversityDTO> getWrappedUniversity(UniversityDTO university) {
        return new WrappedEntity<>(university.getName(), university);
    }
}

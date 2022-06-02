package bobry;

import java.io.Serializable;

public enum InjuryCategory{

        FRACTURE("Fracture"), SPRAIN("Sprain"), DISLOCATION("Dislocation"), COVID("COVID-19");

        private String name;
        InjuryCategory(String name) {
                this.name=name;
        }

        public String toString(){
                return name;
        }
}

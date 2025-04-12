package tn.esprit.tpfoyer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Holiday {
    private String date;
    private String localName;
    private String name;
    private String countryCode;
    private boolean fixed;
    private boolean global;
    private String type;
}

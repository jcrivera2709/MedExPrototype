package Application;

public class Patient {

  private String name;
  private int age;
  private String gender;
  private String chronicCon;

  Patient(String name, int age, String gender, String chronicCon) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.chronicCon = chronicCon;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getChronicCon() {
    return chronicCon;
  }

  public void setChronicCon(String chronicCon) {
    this.chronicCon = chronicCon;
  }
}

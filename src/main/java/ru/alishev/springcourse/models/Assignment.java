package ru.alishev.springcourse.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Assignment")
public class Assignment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Size(min = 2, max = 100)
    @Column(name = "subject_order")
    private String subjectOrder;
    @NotEmpty
    @Size(min = 2, max = 100)
    @Column(name = "sign_control")
    private String signControl;
    @NotEmpty
    @Size(min = 2, max = 100)
    @Column(name = "sign_performance")
    private String signPerformance;
    @NotEmpty
    @Size(min = 2, max = 100)
    @Column(name = "text_order")
    private String textOrder;

    @OneToMany(mappedBy = "assignment")
    private List<User> users;

    public Assignment(String subjectOrder, String signControl, String signPerformance, String textOrder) {
        this.subjectOrder = subjectOrder;
        this.signControl = signControl;
        this.signPerformance = signPerformance;
        this.textOrder = textOrder;
    }

    public Assignment() {
    }

    public String getSubjectOrder() {
        return subjectOrder;
    }

    public void setSubjectOrder(String subjectOrder) {
        this.subjectOrder = subjectOrder;
    }

    public String getSignControl() {
        return signControl;
    }

    public void setSignControl(String signControl) {
        this.signControl = signControl;
    }

    public String getSignPerformance() {
        return signPerformance;
    }

    public void setSignPerformance(String signPerformance) {
        this.signPerformance = signPerformance;
    }

    public String getTextOrder() {
        return textOrder;
    }

    public void setTextOrder(String textOrder) {
        this.textOrder = textOrder;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

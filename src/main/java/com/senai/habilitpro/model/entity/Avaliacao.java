package com.senai.habilitpro.model.entity;

import com.senai.habilitpro.enumerator.AvaliacaoStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Modulo modulo;

    @OneToMany(mappedBy = "avaliacao", fetch = FetchType.LAZY)
    private List<Nota> notas;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AvaliacaoStatusEnum status;
}

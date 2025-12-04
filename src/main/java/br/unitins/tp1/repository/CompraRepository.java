package br.unitins.tp1.repository;

import br.unitins.tp1.model.Compra;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CompraRepository implements PanacheRepository<Compra> {}

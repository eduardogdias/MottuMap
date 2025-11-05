package br.com.fiap.mottumap.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.mottumap.model.entity.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Integer>{

}

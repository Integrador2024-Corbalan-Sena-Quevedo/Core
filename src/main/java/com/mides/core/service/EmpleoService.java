package com.mides.core.service;

import com.mides.core.model.ConocimientosEspecificosEmpleo;
import com.mides.core.model.Empleo;
import com.mides.core.model.Empresa;
import com.mides.core.model.Tarea;
import com.mides.core.repository.IConocimientosEspecificosEmpleoRepository;
import com.mides.core.repository.IEmpleoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmpleoService implements IEmpleoService{

    @Autowired
    IEmpleoRepository empleoRepository;

    @Autowired
    IConocimientosEspecificosEmpleoRepository conocimientosEspecificosEmpleoRepository;
    @Override
    public void saveEmpleo(Empleo empleo) {
        empleoRepository.save(empleo);
    }

    @Override
    public void saveConocimientoEspecificosEmplep(ConocimientosEspecificosEmpleo conocimientosEspecificosEmpleo) {
        conocimientosEspecificosEmpleoRepository.save(conocimientosEspecificosEmpleo);
    }

    @Override
    public Empleo processEmpleo(List<Map<String, String>> csvData, Empresa empresa)  {
        Empleo empleo = new Empleo();

        for (Map<String, String> row : csvData){
            empleo.setNroPuestosDisponible(Integer.parseInt(row.get("No. de puestos que solicita:")));
            empleo.setNombrePuesto(row.get("Nombre del o los puestos:"));
            empleo.setContratoATermino(row.get("¿Es un contrato a termino?:"));
            empleo.setPlazoContrato(row.get("El mismo es por un plazo de..."));
            empleo.setDepartamento(row.get("Departamento:"));
            empleo.setLocalidades(row.get("Localidades"));
            empleo.setCodigo(Integer.parseInt(row.get("Código")));
            empleo.setRitmoImpuesto(row.get("Ritmo impuesto:"));
            empleo.setTrabajoAlExterior(row.get("Trabajo exterior al aire libre:"));
            empleo.setImplicaDesplazamientos(row.get("Implica desplazamientos:"));
            empleo.setSupervisores(row.get("Número de supervisores:"));
            empleo.setCompaneros(row.get("Número de compaeros:"));
            empleo.setSubordinados(row.get("Número de subordinados:"));
            empleo.setEdadPreferente(row.get("Edad preferente:"));
            empleo.setRangoDeEdad(row.get("Rango de edad:"));
            empleo.setFormacionAcademica(row.get("¿Qué formación académica requiere el puesto?:"));
            empleo.setLibretaConducir(row.get("¿Necesita licencia de conducir?:"));
            empleo.setCategoriaLibretaConducir(row.get("Categoría:"));
            empleo.setExperienciaPrevia(row.get("¿Necesita contar con experiencia laboral previa?:"));
            empleo.setTiempoDeExperienciaMinima(row.get("¿De qué período de tiempo?:"));
            empleo.setDisponibilidadHoraria(row.get("Disponibilidad para la tarea:"));
            empleo.setCargaHorariaSemanal(row.get("Carga horaria semanal del puesto:"));
            empleo.setCargaHorariaTipo(row.get("Carga horaria semanal del puesto: - Otros"));
            empleo.setDiasDeSemanaParaCubrirPuesto(row.get("Días de la semana para cubrir el puesto solicitado:"));
            empleo.setDiasDeSemanaParaCubrirPuestoOtro(row.get("Días de la semana para cubrir el puesto solicitado: - Otros"));
            empleo.setDetalleSalarial(row.get("Detalle salarial:"));
            empleo.setRemuneracionOfrecida(row.get("Detalle salarial:"));
            empleo.setTipoRemuneracion(row.get("La remuneración ofrecida es:"));
            empleo.setTipoRemuneracionOtro(row.get("La remuneración ofrecida es: - Otros"));
        }
        empleo.setCategoria("Sin definir");
        empleo.setEmpresa(empresa);
        this.saveEmpleo(empleo);
        return empleo;
    }

    @Override
    public void processConocimientoEspecificosEmpleo(List<Map<String, String>> csvData, Empleo empleo) {
        ConocimientosEspecificosEmpleo conocimientosEspecificosEmpleo = new ConocimientosEspecificosEmpleo();

        for (Map<String, String> row : csvData) {
            conocimientosEspecificosEmpleo.setComputacion(row.get("Computación:"));
            conocimientosEspecificosEmpleo.setOtrosComputacion(row.get("Otros:"));
            conocimientosEspecificosEmpleo.setIdiomas(row.get("Idiomas:"));
            conocimientosEspecificosEmpleo.setIdiomaCompetenciaYrequisito(row.get("Competencias y requisitos_Especificar:"));
            conocimientosEspecificosEmpleo.setIngles(row.get("Inglés:"));
            conocimientosEspecificosEmpleo.setLee(row.get("Lee"));
            conocimientosEspecificosEmpleo.setNivelIdiomasRequeridosEscrituraIngles(row.get("Idiomas requeridos_Escribe:"));
            conocimientosEspecificosEmpleo.setNivelIdiomasRequeridosHablaIngles(row.get("Idiomas requeridos_Habla:"));
            conocimientosEspecificosEmpleo.setNivelIdiomasRequeridosLecturaIngles(row.get("Idiomas requeridos_Lee:"));
            conocimientosEspecificosEmpleo.setPortgues(row.get("Portugués:"));
            conocimientosEspecificosEmpleo.setNivelIdiomasRequeridosEscrituraPortugues(row.get("Idiomas requeridos_Escribe:_2"));
            conocimientosEspecificosEmpleo.setNivelIdiomasRequeridosHablaPortugues(row.get("Idiomas requeridos_Habla:_2"));
            conocimientosEspecificosEmpleo.setNivelIdiomasRequeridosLecturaPortugues(row.get("Idiomas requeridos_Lee:_2"));
        }

        conocimientosEspecificosEmpleo.setEmpleo(empleo);
        this.saveConocimientoEspecificosEmplep(conocimientosEspecificosEmpleo);

    }


}

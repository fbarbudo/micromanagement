import org.junit.Before;
import org.junit.Test;

import java.time.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by fbarbudo on 10/04/16.
 */
public class Testing {

    public static final int WORKING_HOURS_IN_A_DAY = 8;
    public static final int WORKING_DAYS_IN_A_WEEK = 5;
    public static final int DAYS_IN_A_WEEKEND = 2;

    private int workingHoursInADay;
    private Team team;

    @Before
    public void setup() {
        workingHoursInADay = 8;
    }

    /**
     * TODO LIST
     *
     * cuando acabo todos los proyectos
     *
     * cuando acabo un proyecto
     *
     *
     */

    @Test
    public void finaliza_un_proyecto_cuando_no_quedan_horas_por_ejecutar(){
        LocalDate initDate = LocalDate.of(2016, Month.APRIL, 4);

        assertEquals( LocalDate.of(2016, Month.APRIL, 19), calculateFinishDate(initDate, 81, workingHoursInADay));
    }

    @Test
    public void finaliza_un_proyecto_cuando_no_queda_horas_por_ejecutar2(){
        LocalDate initDate = LocalDate.of(2016, Month.APRIL, 4);

        assertEquals( LocalDate.of(2016, Month.APRIL, 5), calculateFinishDate(initDate, 8, workingHoursInADay));
    }

    @Test
    public void no_se_ejecutan_horas_el_dia_de_finalizacion(){
        LocalDate initDate = LocalDate.of(2016, Month.APRIL, 6);

        assertEquals( LocalDate.of(2016, Month.APRIL, 7), calculateFinishDate(initDate, 7, workingHoursInADay));
    }

    @Test
    public void la_fecha_de_fin_no_puede_ser_en_fin_de_semana(){
        LocalDate initDate = LocalDate.of(2016, Month.APRIL, 8);

        assertEquals(LocalDate.of(2016, Month.APRIL, 11), calculateFinishDate(initDate, 8, workingHoursInADay));
    }


    private LocalDate calculateFinishDate(LocalDate initDate, int workingHours, int workingHoursInADay) {

        int calendarDays = calculateCalendarDays(workingHours, workingHoursInADay);

        LocalDate finishDate = initDate.plusDays(calendarDays);

        if (isWeekend(finishDate)){
            int daysToNextMonday = daysToNextMonday(finishDate);
            finishDate = finishDate.plusDays(daysToNextMonday);
        }
        return finishDate;
    }

    private int calculateCalendarDays(int workingHours, int workingHoursInADay) {
        int workingDays = calculateWorkingDays(workingHours, workingHoursInADay);
        int calendarDays = workingDays;
        if ( calendarDays > WORKING_DAYS_IN_A_WEEK){
            calendarDays += (calendarDays / WORKING_DAYS_IN_A_WEEK) * DAYS_IN_A_WEEKEND;
        }
        return calendarDays;
    }

    private int calculateWorkingDays(int hours, int workingHoursInADay) {
        return (hours / workingHoursInADay) + ((hours % workingHoursInADay) > 0? 1 : 0);
    }

    private int daysToNextMonday(LocalDate date) {
        return (DayOfWeek.SUNDAY.getValue() - date.getDayOfWeek().getValue())+1;
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }


    /**
     * TODO_LIST
     *
     * el_trabajo_efectivo_de_un_equipo_es_un_porcentage_de_su_trabajo_diario
     *
     */

    @Test
    public void el_trabajo_efectivo_diario_de_un_equipo_es_un_porcentaje_de_su_trabajo_diario(){
        Team team = new Team(8, 50);

        assertEquals(4, team.calculateEffectiveWorkingHourInADay());
    }

    private class Team {
        private final int workingHoursInADay;
        private final int performance;

        public Team(int workingHoursInADay, int performance) {
            this.workingHoursInADay = workingHoursInADay;
            this.performance = performance;
        }


        public int calculateEffectiveWorkingHourInADay() {
            return (this.workingHoursInADay * performance)/100;
        }
    }
}

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
        Team team = new Team();
        LocalDate initDate = LocalDate.of(2016, Month.APRIL, 4);

        assertEquals( LocalDate.of(2016, Month.APRIL, 19), calculateFinishDate(initDate, 81, team));
    }

    @Test
    public void finaliza_un_proyecto_cuando_no_queda_horas_por_ejecutar2(){
        Team team = new Team();
        LocalDate initDate = LocalDate.of(2016, Month.APRIL, 4);

        assertEquals( LocalDate.of(2016, Month.APRIL, 5), calculateFinishDate(initDate, 8, team));
    }

    @Test
    public void no_se_ejecutan_horas_el_dia_de_finalizacion(){
        Team team = new Team();
        LocalDate initDate = LocalDate.of(2016, Month.APRIL, 6);

        assertEquals( LocalDate.of(2016, Month.APRIL, 7), calculateFinishDate(initDate, 7, team));
    }

    @Test
    public void la_fecha_de_fin_no_puede_ser_en_fin_de_semana(){
        Team team = new Team();
        LocalDate initDate = LocalDate.of(2016, Month.APRIL, 8);

        assertEquals(LocalDate.of(2016, Month.APRIL, 11), calculateFinishDate(initDate, 8, team));
    }


    private LocalDate calculateFinishDate(LocalDate initDate, int workingHours, Team team) {

        int calendarDays = calculateCalendarDays(workingHours);

        LocalDate finishDate = initDate.plusDays(calendarDays);

        if (isWeekend(finishDate)){
            int daysToNextMonday = daysToNextMonday(finishDate);
            finishDate = finishDate.plusDays(daysToNextMonday);
        }
        return finishDate;
    }

    private int calculateCalendarDays(int workingHours) {
        int workingDays = calculateWorkingDays(workingHours);
        int calendarDays = workingDays;
        if ( calendarDays > WORKING_DAYS_IN_A_WEEK ){
            calendarDays += (calendarDays / WORKING_DAYS_IN_A_WEEK) * DAYS_IN_A_WEEKEND;
        }
        return calendarDays;
    }

    private int calculateWorkingDays(int hours) {
        return (hours / WORKING_HOURS_IN_A_DAY) + ((hours % WORKING_HOURS_IN_A_DAY) > 0? 1 : 0);
    }

    private int daysToNextMonday(LocalDate date) {
        return (DayOfWeek.SUNDAY.getValue() - date.getDayOfWeek().getValue())+1;
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    private class Team {
        public int getVelocity() {
            return 16;
        }
    }
}

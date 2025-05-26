package units;

/**
 * Класс CavalryMan представляет юнита 4 уровня - кавалериста.
 * Кавалеристы обладают средней выносливостью (HP),
 * высоким уровнем атаки, средней дистанцией атаки и высокой подвижностью.
 */
public class CavalryMan extends Unit {
    /**
     * Конструктор класса CavalryMan.
     *
     * @param count     Количество кавалеристов в группе.
     */
    public CavalryMan(int count, boolean isDamageUp, boolean isSalonUp, boolean isHotelUp) {
        // Стоимость одного кавалериста в ресурсах.
        this.price = 15;

        // Количество кавалеристов в отряде.
        this.count = count;

        this.HP = 100; //    показатель ХП.
        this.damage = 90; //  Высокий показатель атаки.

        if(isSalonUp){
            this.HP += 1;
        }
        if(isHotelUp){
            this.damage += 1;
        }

        // Общая (суммарная) выносливость группы (зависит от количества и здоровья).
        this.totalHP = this.HP * this.count;

        // Общий (суммарный) урон группы (зависит от количества и базового урона).
        this.totalDamage = this.damage * this.count;

        // Дальность атаки кавалеристов - средний показатель (2 клетки).
        this.distance = 2;

        // Скорость передвижения кавалеристов - высокий показатель (4 клетки).
        this.move = 4;

        // Символьное представление юнита на игровом поле.
        this.design = "%";
    }
}
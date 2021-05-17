import java.util.concurrent.ThreadLocalRandom;

public class HashTable {
    //масив для зберігання елементів
    private Item[] table;
    //кількість елементів в таблиці
    private int count;
    //розмір таблиці
    private int size;

    public HashTable(int size){
        this.size = size;
        table = new Item[size];
    }

    //алгоритм хешування методом Горнера
    private int hash(String value){
        int hash = 0;
        for(int i = 0; i < value.length(); i++){
            hash = (31 * hash + value.charAt(i)) % size;
        }
        return hash;
    }

    //метод вставки
    public void insert(String value){
        Item item = new Item(value);
        int hash = hash(value);
        //цикл задля уникнення колізій
        while (table[hash] != null){
            hash ++;
            hash %= size;
        }
        table[hash] = item;
        count ++;
    }

    //метод доступу
    public String find(String value){
        int hash = hash(value);
        while (table[hash] != null){
            if(table[hash].getValue().equals(value)){
                return table[hash].getValue();
            }
            hash ++;
            hash = hash % size;
        }
        return null;
    }

    //метод видалення
    public void remove(String value){
        int hash = hash(value);
        while (table[hash] != null){
            if(table[hash].getValue().equals(value)){
                table[hash] = null;
            }
            hash ++;
            hash = hash % size;
        }
    }

    //вивести вміст таблиці
    public void print(){
        for(int i = 0; i < size; i++){
            if(table[i] != null){
                System.out.println(i + " " + table[i].getValue());
            }
        }
    }


    public static void main(String[] args) {
        HashTable ht = new HashTable(100);
        ht.insert("car");
        ht.insert("water");
        ht.print();
        System.out.println(ht.find("cur"));
        ht.remove("car");
        ht.print();

        HashTable htTest = new HashTable(10000);
        long start = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++){
            htTest.insert(String.valueOf(i));
        }
        System.out.println("Time for adding: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++){
            htTest.find(String.valueOf(ThreadLocalRandom.current().nextInt(1, 10000)));
        }
        System.out.println("Time to get elements: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++){
            int tmp = ThreadLocalRandom.current().nextInt(1, 10000);
            htTest.remove(String.valueOf(tmp));
        }

        System.out.println("Time to remove elements: " + (System.currentTimeMillis() - start));


    }

}

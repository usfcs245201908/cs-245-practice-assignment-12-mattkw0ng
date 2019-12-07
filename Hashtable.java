import java.lang.*;

public class Hashtable{
    class HashNode{
        String key;
        String value;
        HashNode next;
        HashNode(String key, String value){
            this.key = key;
            this.value = value;
            next = null;
        }
    }

    HashNode[] table = new HashNode[1000];
    int size = 1000;
    int entries = 0;

    public int getHashCode(String key){
        int code = Math.abs(key.hashCode());
        while(code>size){
            code = code/10;
        }
        return code;
    }

    public void put(String key, String value){
        int code = getHashCode(key)%size;

        HashNode head = table[code];
        if(head == null){
            table[code] = new HashNode(key,value);
            entries++;
        }else{
            while(head.next != null) {
                head = head.next;
                if (head.key.equals(key)) {
                    head.value = value;     //if the key is already there, update its value
                    return;
                }
            }
            HashNode new_entry = new HashNode(key,value);
            head = table[code];
            new_entry.next = head;    //sets the next value to the original head
            table[code] = new_entry;        //makes the new_entry the head;

            entries++;
        }

        if((entries*1.0)/size >= 0.5) {   //load threshold = 0.5
            expandTable();
        }
    }

    public boolean containsKey(String key){
        int code = getHashCode(key)%size;

        HashNode current = table[code];
        if(current==null){
            return false;
        }else if(current.key.equals(key)){
            return true;
        }else{
            while(current.next!=null){
                current = current.next;
                if(current.key.equals(key)){
                    return true;
                }
            }
            return false;
        }


    }

    public String get(String key){
        int code = getHashCode(key)%size;
        HashNode current = table[code];
        if(table[code] == null){
            return null;
        }
        else if(current.key.equals(key)){
            return current.value;
        }else{
            while(current.next!=null){
                current = current.next;
                if(current.key.equals(key)){
                    return current.value;
                }
            }
            return null;
        }
    }

    public String remove(String key) throws Exception{
        int code = getHashCode(key)%size;
        HashNode current = table[code];
        if(current.key.equals(key)){
            if(current.next != null){
                table[code] = current.next;
            }else{
                table[code] = null;
            }
            entries--;
            return current.value;
        }else{
            HashNode prev = current;    //keeps track of the previous node;
            while(current.next!=null){
                current = current.next;         //increments current
                if(current.key.equals(key)){
                    prev.next = current.next;   //deleting the node
                    entries--;
                    return current.value;
                }
                prev = prev.next;
            }
            throw new Exception();
        }
    }

    public void expandTable(){
        HashNode[] new_table = new HashNode[size*size];    //resets the table to be bigger
        String[] keys = new String[entries];
        String[] values = new String[entries];
        int index = 0;
        for(int i = 0; i<size ; i++){
            if(table[i] != null){
                HashNode current = table[i];
                keys[index] = current.key;
                values[index++] = current.value;
                if(current.next!=null){
                    while(current.next!=null){
                        current = current.next;
                        keys[index] = current.key;
                        values[index++] = current.value;
                    }
                }

            }
        }
        size = size*size;

        for(int i = 0 ; i<entries ; i++) {
            String key = keys[i];
            String value = values[i];
            int code = getHashCode(key)%size;
            HashNode head = new_table[code];

            if (head == null) {
                new_table[code] = new HashNode(key, value);
            } else {
                while (head.next != null) {
                    head = head.next;
                    if (head.key.equals(key)) {
                        head.value = value;     //if the key is already there, update its value
                        return;
                    }
                }
                HashNode new_entry = new HashNode(key, value);
                head = new_table[code];
                new_entry.next = head;    //sets the next value to the original head
                new_table[code] = new_entry;        //makes the new_entry the head;

            }
        }
        table = new_table;
    }



}
package piggeon.engine;

import java.io.*;
import java.util.HashMap;

public class SaveState implements Serializable
{
    private HashMap<Integer, Stage> stages;

    public SaveState()
    {
        stages = new HashMap<>();
    }

    public boolean isEmpty()
    {
        return stages.isEmpty();
    }

    public void addStage(int index, Stage stage)
    {
        stages.put(index, stage);
    }

    public Stage getStage(int index)
    {
        return stages.get(1);
    }

    public void saveToFile(String filename)
    {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(filename);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }finally
        {
            if(fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                }catch(IOException e2)
                {
                    e2.printStackTrace();
                }
            }

            if(objectOutputStream != null)
            {
                try
                {
                    objectOutputStream.close();
                }catch(IOException ex1)
                {
                    ex1.printStackTrace();
                }
            }
        }

    }

    public static SaveState loadFromFile(String filename)
    {
        SaveState saveState = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try
        {
            File file = new File(filename);
            if(file.exists())
            {
                fileInputStream = new FileInputStream(filename);
                objectInputStream = new ObjectInputStream(fileInputStream);
                saveState = (SaveState) objectInputStream.readObject();
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }finally
        {
            if(fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }catch(IOException e2)
                {
                    e2.printStackTrace();
                }
            }

            if(objectInputStream != null)
            {
                try
                {
                    objectInputStream.close();
                }catch(IOException ex1)
                {
                    ex1.printStackTrace();
                }
            }
        }

        return saveState;
    }
}

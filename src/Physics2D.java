package src;

public class Physics2D
{
    
    public interface Mass{
        public float getVelocityX();
        public float getVelocityY();
        public float getMass();
        public float getAccelerationX();
        
        public void setVelocityX(float x);
        public void setVelocityY(float y);
        public void setAccelerationX(float a);
    }
    
}

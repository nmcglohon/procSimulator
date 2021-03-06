/* 
 *  --- Operating Systems Homework 2 --- 
 * Copyright (c) 2014, Mark Plagge -- plaggm
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */



import java.util.HashMap;


/**
 *
 * @author Mark
 */
public abstract class AbstractProcess {
    long pid;
    boolean isInteractive;

    long cpuTimeNeeded;
    
    //Initial values (set via constructor)
    long vWait;
    long ioWait;
    /** burstValue is the total CPU time needed. **/
    long burstValue;
    /** burstNums is the number of CPU burst times left **/
    long burstNums;
    long priority;
    long userWait;
    
    long startTime;
    //Calculated Values (xxxTime is time spent in a state)

    ProcessState pState;
  
    private static final String pidStr = "Process ID: ";
    private static final String sttStr = "|| Start Stime: ";
    private static final String twtStr = "|| Total Wait Time: ";
    private static final String tioStr = "|| Total IO Waiting Time: ";
    HashMap<ProcessState, Integer> timings;
         
         

    public AbstractProcess(int pid, boolean isInteractive,int burstNums, int priority ) {
        this.pid = pid;
        this.isInteractive = isInteractive;
       
        this.burstNums = burstNums;
        this.priority = priority;

        this.pState = ProcessState.idle;
        //set up timing table:
                timings = new HashMap<>(6);
        int it = 0;
       
        timings.put(ProcessState.idle, it);
        timings.put(ProcessState.active, it);
        timings.put(ProcessState.contextSwitch, it);
        timings.put(ProcessState.terminated, it);
        timings.put(ProcessState.userWait, it);
        timings.put(ProcessState.IOWait, it);
    }
    public boolean isInteractive()
    {
        return this.isInteractive;  
    }
    //Timing getter:

    public abstract long getTiming(String t);
    

    public abstract long getTiming(ProcessState stq);
    //return timings (current process times):
      public long getActiveTime() {
        return getTiming(ProcessState.active);
    }
   
    public long getIoWaitTime() {
        return getTiming(ProcessState.IOWait);
    }
    public long getUserWaitTime() {
        return getTiming(ProcessState.userWait);
    }
    public long getCtxSwitchTime() {
        return getTiming(ProcessState.contextSwitch);
    }
    public long getIdleTime() {
        return getTiming(ProcessState.idle);
    }

    
    public long getRemainingBursts()
    {
        return this.burstValue;
    }
    public long getBurstValue() {
        return burstValue;
    }
       //Process stat methods
    public long getStartTime() {
        return startTime;
    }

    public long getPriority() {
        return priority;
    }
    public long getPid() {
        return pid;
    }
    
    
    @Override
    public String toString() {
        return pidStr + this.getPid() + sttStr + this.getStartTime() + twtStr + 
                getTotalWaitTime()+ tioStr + getIoWaitTime();
    }
    /** Setters Below
     * @param state **/
    public void setState(ProcessState state){
        this.pState = state;
    }
    //process remaining time(calculated values)
    public abstract long getTotalWaitTime();
     abstract long remIoWait();

    
     abstract long remUserWait();
    
    

    
    //Abstract (need to implement) methods:
    
    abstract public void tick();
    abstract public boolean isDone();
    
    
    
}

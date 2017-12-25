package com.raywenderlich.camelot;

import org.researchstack.backbone.result.TaskResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.task.OrderedTask;

import java.util.List;

/**
 * Created by Vikas Deshpande on 11/9/2017.
 */

class CustomOrderedTask extends OrderedTask
{

    public CustomOrderedTask(String identifier, List<Step> steps) {
        super(identifier, steps);
    }

    public CustomOrderedTask(String identifier, Step... steps) {
        super(identifier, steps);

    }


    @Override
    public Step getStepAfterStep(Step step, TaskResult result)
    {
        if(step==null)
        {
            return steps.get(0);
        }

        else if(step.getIdentifier().equals("q1"))
        {
            if(result.getStepResult(step.getIdentifier()).getResult().toString().equals("1"))
            {
                return steps.get(7);
            }
            else
            {
                return steps.get(2);
            }
        }
        else if(step.getIdentifier().equals("q2"))
        {
            if(result.getStepResult(step.getIdentifier()).getResult().toString().equals("1"))
            {
                return steps.get(7);
            }
            else
            {
                return steps.get(3);
            }
        }
        int nextIndex = steps.indexOf(step)+1;
        if (nextIndex<steps.size())
        {
            return steps.get(nextIndex);
        }
        return null;
    }


    @Override
    public Step getStepBeforeStep(Step step, TaskResult result)
    {
        int nextIndex = steps.indexOf(step)-1;

        if(step.getIdentifier().equals("i3"))
        {
            if(result.getStepResult("q1").getResult().toString().equals("1"))
            {
                return steps.get(1);
            }
            else if(result.getStepResult("q2").getResult().toString().equals("1"))
            {
                return steps.get(2);
            }
            else
            {
                return steps.get(nextIndex);
            }
        }
        if(nextIndex>=0)
        {
            return steps.get(nextIndex);
        }
        return null;
    }

}

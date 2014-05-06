/*******************************************************************************
 * Copyright (c) 2010-2014, Miklos Foldenyi, Andras Szabolcs Nagy, Abel Hegedus, Akos Horvath, Zoltan Ujhelyi and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *   Miklos Foldenyi - initial API and implementation
 *   Andras Szabolcs Nagy - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.dse.api.strategy.impl;

import java.util.List;

import org.eclipse.viatra.dse.api.strategy.interfaces.INextTransition;
import org.eclipse.viatra.dse.base.DesignSpaceManager;
import org.eclipse.viatra.dse.base.GlobalContext;
import org.eclipse.viatra.dse.base.ThreadContext;
import org.eclipse.viatra.dse.designspace.api.ITransition;
import org.eclipse.viatra.dse.monitor.PerformanceMonitorManager;

public class DepthFirstNextTransition implements INextTransition {

    private static final String UNDO_TIMER = "undoTimer";
    private static final String GET_LOCAL_FIREABLE_TRANSITIONS = "getLocalFireableTransitions";

    public class SharedData {
        public int maxDepth;
    }

    private int baseDepth = 0;
    private int initMaxDepth = Integer.MAX_VALUE;
    private SharedData sharedData;

    public DepthFirstNextTransition() {
    }

    public DepthFirstNextTransition(int maxDepth) {
        initMaxDepth = maxDepth;
    }

    @Override
    public void init(ThreadContext context) {
        GlobalContext gc = context.getGlobalContext();
        if (gc.getSharedObject() == null) {
            sharedData = new SharedData();
            sharedData.maxDepth = initMaxDepth;
            gc.setSharedObject(sharedData);
        } else {
            sharedData = (SharedData) gc.getSharedObject();
        }

        baseDepth = context.getDesignSpaceManager().getTrajectoryInfo().getDepthFromRoot();
    }

    @Override
    public ITransition getNextTransition(ThreadContext context, boolean lastWasSuccesful) {

        DesignSpaceManager dsm = context.getDesignSpaceManager();
        PerformanceMonitorManager.startTimer(GET_LOCAL_FIREABLE_TRANSITIONS);
        List<? extends ITransition> transitions = dsm.getUntraversedTransitionsFromCurrentState();
        PerformanceMonitorManager.endTimer(GET_LOCAL_FIREABLE_TRANSITIONS);

        // backtrack
        while (transitions == null || transitions.isEmpty()
                || baseDepth + dsm.getTrajectoryInfo().getDepthFromCrawlerRoot() >= sharedData.maxDepth) {
            PerformanceMonitorManager.startTimer(UNDO_TIMER);
            boolean didUndo = dsm.undoLastTransformation();
            PerformanceMonitorManager.endTimer(UNDO_TIMER);
            if (!didUndo) {
                return null;
            }

            PerformanceMonitorManager.startTimer(GET_LOCAL_FIREABLE_TRANSITIONS);
            transitions = dsm.getUntraversedTransitionsFromCurrentState();
            PerformanceMonitorManager.endTimer(GET_LOCAL_FIREABLE_TRANSITIONS);
        }

        if (transitions.size() > 1 && context.getGlobalContext().canStartNewThread()) {
            context.getGlobalContext().tryStartNewThread(context);
        }

        return transitions.iterator().next();
    }

    @Override
    public void newStateIsProcessed(ThreadContext context, boolean isAlreadyTraversed, boolean isGoalState,
            boolean constraintsNotSatisfied) {
        if (isAlreadyTraversed || isGoalState || constraintsNotSatisfied) {
            context.getDesignSpaceManager().undoLastTransformation();
        }
    }

}

package org.firstinspires.ftc.teamcode.util.math;
import org.ejml.simple.SimpleMatrix;
import org.firstinspires.ftc.teamcode.drive.swerve.SwerveModuleState;

import java.util.Arrays;

public class SwerveKinematics {
    public SimpleMatrix forwardmatrix;
    public SimpleMatrix inversematrix;

    public int m_numModules;
    public Coordinate[] m_modules;
    private SwerveModuleState[] m_moduleStates;


    public SwerveKinematics(Coordinate[] wheelsMeters) {
        if (wheelsMeters.length < 2) {
            throw new IllegalArgumentException("A swerve drive requires at least two modules");
        }

        m_numModules = wheelsMeters.length;
        m_modules = Arrays.copyOf(wheelsMeters, m_numModules);
        m_moduleStates = new SwerveModuleState[m_numModules];
        Arrays.fill(m_moduleStates, new SwerveModuleState());
        inversematrix = new SimpleMatrix(m_numModules * 2, 3);
        for (int i = 0; i < m_numModules; i++) {
            inversematrix.setRow(i * 2 + 0, 0, /* Start Data */ 1, 0, -m_modules[i].y);
            inversematrix.setRow(i * 2 + 1, 0, /* Start Data */ 0, 1, +m_modules[i].x);
        }
        forwardmatrix = inversematrix.pseudoInverse();
//        MathSharedStore.reportUsage(MathUsageId.kKinematics_SwerveDrive, 1);
    }

    public double[] moduletochasis(SwerveModuleState[]wheelStates){ // FrontRight, Front Left, Back Left, Back Right
        SimpleMatrix moduleStatesMatrix = new SimpleMatrix(4 * 2, 1); // 8 by 1 vector
        for (int i = 0; i < 4; i++) {
                SwerveModuleState modulestate = wheelStates[i];
                  moduleStatesMatrix.set(i * 2, 0, modulestate.velocity * Math.cos(modulestate.angle));
                        moduleStatesMatrix.set(i * 2 + 1, modulestate.velocity * Math.sin(modulestate.angle));
        }

        SimpleMatrix chassisSpeedsVector = forwardmatrix.mult(moduleStatesMatrix);
        double[] chasis_vel = new double[]{
                chassisSpeedsVector.get(0, 0),
                chassisSpeedsVector.get(1, 0),
                chassisSpeedsVector.get(2, 0)
        };
        return chasis_vel;
    }
}

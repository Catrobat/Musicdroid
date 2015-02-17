/*
 * Musicdroid: An on-device music generator for Android
 * Copyright (C) 2010-2014 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.pocketmusic.test.note;

import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;

public class ProjectTest extends AndroidTestCase {

	public void testGetBeatsPerMinute() {
        int beatsPerMinute = 60;
		Project project = ProjectTestDataFactory.createProject(beatsPerMinute);

		assertEquals(beatsPerMinute, project.getBeatsPerMinute());
	}

    public void testGetName() {
        String name = "TestName";
        Project project = ProjectTestDataFactory.createProject(name);

        assertEquals(name, project.getName());
    }

    public void testSetName() {
        String name = "SomeNewName";
        Project project = new Project(ProjectTestDataFactory.createProject(), name);

        assertEquals(name, project.getName());
    }

	public void testAddTrack() {
		Project project = ProjectTestDataFactory.createProject();
        Track track = TrackTestDataFactory.createTrack();
		project.addTrack(track);

		assertEquals(1, project.size());
        assertEquals(0, track.getId());
        assertEquals(project, track.getProject());
	}

	public void testGetTrack() {
		Project project = ProjectTestDataFactory.createProject();
		Track track = TrackTestDataFactory.createTrack();
		project.addTrack(track);

		assertEquals(track, project.getTrack(0));
	}

	public void testRemoveTrack() {
		Project project = ProjectTestDataFactory.createProject();
		Track track = TrackTestDataFactory.createTrack();
		project.addTrack(track);
		project.removeTrack(track);

		assertEquals(0, project.size());
	}

	public void testEquals1() {
		Project project1 = ProjectTestDataFactory.createProject();
		Project project2 = ProjectTestDataFactory.createProject();

		assertTrue(project1.equals(project2));
	}

	public void testEquals2() {
		Project project1 = ProjectTestDataFactory.createProjectWithTrack();
		Project project2 = ProjectTestDataFactory.createProjectWithTrack();

		assertTrue(project1.equals(project2));
	}

	public void testEquals3() {
		Project project1 = ProjectTestDataFactory.createProjectWithTrack();
		Project project2 = ProjectTestDataFactory.createProjectWithTrack(MusicalInstrument.APPLAUSE);

		assertFalse(project1.equals(project2));
	}

	public void testEquals4() {
		Project project1 = ProjectTestDataFactory.createProjectWithTrack();
		Project project2 = ProjectTestDataFactory.createProject();

		assertFalse(project1.equals(project2));
	}

    public void testEquals5() {
        Project project1 = ProjectTestDataFactory.createProject("Some name");
        Project project2 = ProjectTestDataFactory.createProject("Another name");

        assertFalse(project1.equals(project2));
    }

    public void testEquals6() {
        Project project1 = ProjectTestDataFactory.createProject(60);
        Project project2 = ProjectTestDataFactory.createProject(90);

        assertFalse(project1.equals(project2));
    }

	public void testEquals7() {
		Project project = ProjectTestDataFactory.createProject();

		assertFalse(project.equals(null));
	}

	public void testEquals8() {
		Project project = ProjectTestDataFactory.createProject();

		assertFalse(project.equals(""));
	}

	public void testToString() {
		Project project = ProjectTestDataFactory.createProject();
		String expectedString = "[Project] name=" + project.getName() + " beatsPerMinute="
				+ project.getBeatsPerMinute() + " trackCount=" + project.size();

		assertEquals(expectedString, project.toString());
	}

    public void testCopyProject() {
        Project project = ProjectTestDataFactory.createProjectWithTrack();
        Project copyProject = new Project(project);

        assertTrue(project != copyProject);
        assertTrue(project.equals(copyProject));
    }
}

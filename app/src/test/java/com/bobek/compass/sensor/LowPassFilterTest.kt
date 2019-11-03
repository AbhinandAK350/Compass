/*
 * This file is part of Compass.
 * Copyright (C) 2019 Philipp Bobek <philipp.bobek@mailbox.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Compass is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.bobek.compass.sensor

import org.junit.Assert.assertEquals
import org.junit.Test

class LowPassFilterTest {

    @Test(expected = IllegalStateException::class)
    fun constructorThrowsExceptionWhenAlphaIsTooSmall() {
        LowPassFilter(0.0f)
    }

    @Test
    fun filter() {
        val lowPassFilter = LowPassFilter(1f)

        assertEquals(
            SensorValues(0.0f, 1.0f, 2.0f, 1_000_000_000L),
            lowPassFilter.filter(SensorValues(0.0f, 1.0f, 2.0f, 1_000_000_000L))
        )

        assertEquals(
            SensorValues(0.5f, 1.0f, 1.5f, 2_000_000_000L),
            lowPassFilter.filter(SensorValues(1.0f, 1.0f, 1.0f, 2_000_000_000L))
        )

        assertEquals(
            SensorValues(0.75f, 1.0f, 1.25f, 3_000_000_000L),
            lowPassFilter.filter(SensorValues(1.0f, 1.0f, 1.0f, 3_000_000_000L))
        )

        assertEquals(
            SensorValues(0.875f, 1.0f, 1.125f, 4_000_000_000L),
            lowPassFilter.filter(SensorValues(1.0f, 1.0f, 1.0f, 4_000_000_000L))
        )

        assertEquals(
            SensorValues(0.9375f, 1.0f, 1.0625f, 5_000_000_000L),
            lowPassFilter.filter(SensorValues(1.0f, 1.0f, 1.0f, 5_000_000_000L))
        )

        assertEquals(
            SensorValues(0.96875f, 1.0f, 1.03125f, 6_000_000_000L),
            lowPassFilter.filter(SensorValues(1.0f, 1.0f, 1.0f, 6_000_000_000L))
        )
    }

    @Test
    fun reset() {
        val lowPassFilter = LowPassFilter(1f)

        assertEquals(
            SensorValues(0.0f, 0.0f, 0.0f, 1_000_000_000L),
            lowPassFilter.filter(SensorValues(0.0f, 0.0f, 0.0f, 1_000_000_000L))
        )

        assertEquals(
            SensorValues(0.5f, 0.5f, 0.5f, 2_000_000_000L),
            lowPassFilter.filter(SensorValues(1.0f, 1.0f, 1.0f, 2_000_000_000L))
        )

        lowPassFilter.reset()

        assertEquals(
            SensorValues(0.0f, 0.0f, 0.0f, 3_000_000_000L),
            lowPassFilter.filter(SensorValues(0.0f, 0.0f, 0.0f, 3_000_000_000L))
        )

        assertEquals(
            SensorValues(0.5f, 0.5f, 0.5f, 4_000_000_000L),
            lowPassFilter.filter(SensorValues(1.0f, 1.0f, 1.0f, 4_000_000_000L))
        )
    }
}

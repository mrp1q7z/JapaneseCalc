/*
 * Copyright (C) 2013 YojiokiSoft
 * 
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this
 * program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.yojiokisoft.japanesecalc;

import java.math.BigDecimal;

/**
 * 四則演算オペレーション
 */
public enum Operation {
	PLUS {
		BigDecimal eval(BigDecimal x, BigDecimal y) {
			return x.add(y);
		}
	},
	MINUS {
		BigDecimal eval(BigDecimal x, BigDecimal y) {
			return x.subtract(y);
		}
	},
	TIMES {
		BigDecimal eval(BigDecimal x, BigDecimal y) {
			BigDecimal z = x.multiply(y);
			return z.setScale(AbstractDisplay.DISPLAY_DIGIT - 1, BigDecimal.ROUND_HALF_UP);
		}
	},
	DIVIDE {
		BigDecimal eval(BigDecimal x, BigDecimal y) {
			return x.divide(y, AbstractDisplay.DISPLAY_DIGIT, BigDecimal.ROUND_HALF_UP);
		}
	};
	abstract BigDecimal eval(BigDecimal x, BigDecimal y);
}

CREATE TABLE `user` (
  `id` varchar(20) NOT NULL DEFAULT '',
  `userName` varchar(20) DEFAULT NULL,
  `emailId` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `student` (
  `id` varchar(20) NOT NULL DEFAULT '',
  `feeStatus` varchar(20) DEFAULT NULL,
  `isApproved` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `professor` (
  `id` varchar(20) NOT NULL DEFAULT '',
  `department` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `course` (
  `courseId` varchar(15) NOT NULL DEFAULT '',
  `couseName` varchar(20) DEFAULT NULL,
  `profId` varchar(20) NOT NULL,
  `seats` int(11) DEFAULT NULL,
  PRIMARY KEY (`courseId`),
  KEY `profId` (`profId`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`profId`) REFERENCES `professor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `gradecard` (
  `id` varchar(20) NOT NULL DEFAULT '',
  `courseId` varchar(20),
  `grade` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`, `courseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `registrar` (
  `id` varchar(20) NOT NULL DEFAULT '',
  `courseId` varchar(20) NOT NULL,
  `grade` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`,`courseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

package com.me.maee;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MainClass {
	public static void main(String[] args) {
			LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
			cfg.title = "EpicGaem";
			cfg.useGL20 = false;
			cfg.fullscreen = false;
			cfg.vSyncEnabled = true;
			
			new LwjglApplication(new MAE(), cfg);
		}
	}

//? ����� �����-����� � ������� �������
//? ���������� �� ����� �� ������
//?! ����������� ���������� ��������� �����
//? ����������� getDeltaTime();
//? ���������� � ��������� ������
// 1. ������ � ������, �������, ����������, ����������
// - velocity - CHECK
//   impulse  - CHECK
//   force    - CHECK
//   mass     - CHECK
//   rotation
// 2. ��������� ��������
//	����� �������� ��� ���� �������� - CHECK
//	? ��������������� ��������
//	���������� ��������� - CHECK
//	? �������������
//	? ���-������
// 3. �����������!
//	QuadTree - CHECK
// 	Groups of objects
// 4. ���� � �����������
//
//
//!!!! ��������
//!!! ��������� �������� ��������
//!! ���������� ���������� ��������
//!!! Shape Vs Shape
//!! ������������ �� ������
//! ����������/���������� �������������� Body-Circle - ���� ��� ��������
//!! ��������� ��������� �������� ���������������
//! ��������� ����� ��� ����� �������
// ��������� ����
//������-�� ���� ��������� �� 0Y �� �������� �������� ���� - �������� ��������� �� ��� ���, ���� �� ���� ������� ��������
//��� ������ defineMass() ��� �������� - ������-�� ���������� ���� �����, ����� �������� mass �� ����������. �������� � defineMass?
//�������� float �� double - ����, �����?
//����� ���������� �� ���� - ����� ����������� � �������� ��������� - �������� �������� ����������, �� � ���� ��� ��� ���� �����������
//�������� ������ ������ ���������, � ������� ����� ���� �������� � ����
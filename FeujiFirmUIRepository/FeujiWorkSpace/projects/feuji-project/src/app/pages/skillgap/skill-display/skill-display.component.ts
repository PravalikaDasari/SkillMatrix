import { Component, Inject } from '@angular/core';
import { SkillRepo } from '../../../../models/skill.repo';
import { Skill } from '../../../../models/skill.model';
import { SkillService } from '../../../../models/skill.service';
import { HttpClient } from '@angular/common/http';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { SkillData } from '../../../../models/SkillData.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-skill-display',
  templateUrl: './skill-display.component.html',
  styleUrl: './skill-display.component.css'
})
export class SkillDisplayComponent {
  newSkillName: string = '';
  newSkillDescription: string = '';
  accordionSubData: any[] = [];
  selectedSubSkillCategoryId: number = 0;
  dialogRef: any;
  status: number = 0;
  skillCategoryName: string = '';
  skillCategoryId: number = 0;
  referenceTypeRecord: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private http: HttpClient,
    private employeeSkillService: EmployeeSkillService,
    public dialog: MatDialog,
    private skillDataSevice: SkillData
  ) { }

  ngOnInit(): void {
    this.selectedSubSkillCategoryId = this.data.selectedSubSkillCategory;
    this.skillCategoryName = this.data.selectedSkillCategory;
    this.skillDataSevice.accordionSubData$.subscribe(subData => {
      this.accordionSubData = subData;
    });
  }

  close(): void {
    this.dialogRef.close();
  }

  saveSkill() {
    this.employeeSkillService.getSkillCategoryTypeId(this.skillCategoryName)
      .subscribe(response => {
        this.referenceTypeRecord = response
        this.skillCategoryId = this.referenceTypeRecord.referenceTypeId;
       
        if (this.newSkillName.trim() !== '') {
          const skillBean: Skill = new Skill(
            this.newSkillName,
            this.selectedSubSkillCategoryId,
            this.skillCategoryId,
            this.newSkillDescription,
            1,
            'Admin', 
            'Admin'
          );
          this.employeeSkillService.saveSkillAdmin(skillBean).subscribe(
            (response: any) => {
              Swal.fire({
                title: "Skill saved successfully",
                icon: "success"
              })
              this.dialogRef.close();
            },
            (error: any) => {
              Swal.fire({
                title: "Skill already present",
                icon: "error"
              })
              this.dialogRef.close();
            }
          );
        } else {
          console.log('New sub skill category name is empty or not defined');
        }
      })
  }
}

import { Component, Inject, OnInit } from '@angular/core';
import { EmployeeSkillService } from '../../../services/employee-skill.service';
import { SubSkillCategoryBean } from '../../../../models/SubSkillCategoryBean.model';
import { SubSkillData } from '../../../../models/SubSkillData.service';
import Swal from 'sweetalert2';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-sub-skill-category',
  templateUrl: './add-sub-skill-category.component.html',
  styleUrl: './add-sub-skill-category.component.css'
})
export class AddSubSkillCategoryComponent implements OnInit {
  accordionData: any[] = []; 
  accordionSubData: any[] = []; 
  panelOpenState: boolean = false;
  selectedSkillCategory: any;
  newSkillCategoryName: any;
  referenceTypeId: any;
  referenceTypeRecord:any;
  newSubSkillCategoryName: string = '';

  selectedsubskillcategory: string = '';

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<AddSubSkillCategoryComponent>,
    private employeeSkillService: EmployeeSkillService
  ) { }

  ngOnInit(): void {
    this.selectedsubskillcategory = this.data.selectedsubskillcategory;
  }
  close(selectedCategory: any): void {
    this.dialogRef.close(selectedCategory);
  }


  saveSubSkillCategory() {
  
    this.employeeSkillService.getSkillCategoryTypeId(this.selectedsubskillcategory)
      .subscribe(response => {
        this.referenceTypeRecord = response
  
        if (this.newSubSkillCategoryName.trim() !== '') {
          const subskillCategoryBean: SubSkillCategoryBean = new SubSkillCategoryBean(
            this.newSubSkillCategoryName,
            {
              referenceTypeId: this.referenceTypeRecord.referenceTypeId,
              referenceTypeName: this.newSubSkillCategoryName
            },
          );
  
  
          this.employeeSkillService.saveSubSkillCategoryAdmin(subskillCategoryBean).subscribe(
            response => {
              Swal.fire({
                title: "Sub Skill category saved successfully",
                icon: "success"
              })
              this.dialogRef.close(this.selectedCategory);
            },
            error => {
              Swal.fire({
                title: "Sub Skill category already present",
                icon: "error"
              })
              this.dialogRef.close();
            }
          );
        } else {
          return;
        }
      });
  }
  


  selectedCategory(selectedCategory: any) {
    throw new Error('Method not implemented.');
  }


}

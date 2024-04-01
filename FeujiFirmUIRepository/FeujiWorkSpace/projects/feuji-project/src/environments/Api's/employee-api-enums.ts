export enum ControllerPaths {
  EmployeeSkillController='/employeeskill',
  ReferenceController='/referencedetails',
  SkillController='/skill'
 }
 
 export enum Paths {
   Fetch='/fetch/',
   Delete=ControllerPaths.EmployeeSkillController+'/delete/',
   Save=ControllerPaths.EmployeeSkillController+'/saverecord',
   Update=ControllerPaths.EmployeeSkillController+'/update/',
   FetchAll=ControllerPaths.EmployeeSkillController+'/getAll/',
   Getreference=ControllerPaths.ReferenceController+'/getref/',
   FetchSkillNames=ControllerPaths.SkillController+'/getSkillNames/',
   FetchRoles='/getRoles/',
   FetchEmployeeSkills='/fetchBySkillId/',
   GetAllSkills=ControllerPaths.SkillController+'/getAll/',
   GetAllSkillsForEmployee=ControllerPaths.SkillController+'/getAllForEmployee/',
   DeletCategory=ControllerPaths.ReferenceController+'/deleteSkillCategory/',
   DeleteSubCategory=ControllerPaths.ReferenceController+'/deleteSubskill/'
 }  